package app.onlineschool.controller;

import app.onlineschool.dto.TestShowPage;
import app.onlineschool.exception.ResourceNotFoundException;
import app.onlineschool.model.*;
import app.onlineschool.repository.*;
import com.ibm.icu.impl.InvalidFormatException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * Full test CRUD
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestRepository testRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Creates a test
     * @param courseId
     * @param lessonNum
     * @param principal
     * @return redirect to first test question
     */
    @GetMapping("/{courseId}/{lessonNum}/create")
    public String index(@PathVariable Long courseId, @PathVariable int lessonNum, Principal principal) {

        if (!userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .isAdmin()) {
            return "redirect:/courses/" + courseId + "/" + lessonNum;
        }

        Lesson lesson = lessonRepository.findByCourseIdAndLessonNumber(courseId, lessonNum)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        Optional<Test> test = testRepository.findByLessonId(lesson.getId());
        if (test.isEmpty()) {
            Test newTest = new Test();
            newTest.setLesson(lesson);

            Question newQuestion = new Question();
            newQuestion.setContent("Untitled");
            newQuestion.setNumberInLesson(1);
            newQuestion.setTest(newTest);
            newQuestion.setAnswers(new ArrayList<>());

            Answer newAnswer = new Answer();
            newAnswer.setIsCorrect(0);
            newAnswer.setContent("Answer 1");
            newAnswer.setQuestion(newQuestion);

            newQuestion.getAnswers().add(newAnswer);
            newTest.setQuestions(new ArrayList<>());
            newTest.getQuestions().add(newQuestion);

            testRepository.save(newTest);

        }

        return "redirect:/test/" + courseId + "/" + lessonNum + "/1/edit";
    }

    /**
     * Shows test page for editing
     * @param courseId
     * @param lessonNum
     * @param questionNumber
     * @param model
     * @param principal
     * @return test edit page
     */
    @GetMapping("/{courseId}/{lessonNum}/{questionNumber}/edit")
    public String viewEdit(@PathVariable Long courseId,
                    @PathVariable int lessonNum,
                    @PathVariable Integer questionNumber,
                    Model model,
                    Principal principal) {

        if (!userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .isAdmin()) {
            return "redirect:/course/" + courseId + "/" + lessonNum;
        }

        // Get lesson and test
        Lesson lesson = lessonRepository.findByCourseIdAndLessonNumber(courseId, lessonNum)
                .orElse(null);
        if (lesson == null) {
            return "redirect:/course/" + courseId + "/" + lessonNum;
        }

        Test test = testRepository.findByLessonId(lesson.getId())
                .orElseGet(() -> {
                    Test newTest = new Test();
                    newTest.setLesson(lesson);
                    return testRepository.save(newTest);
                });

        List<Question> questions = test.getQuestions();
        if (questions == null) {
            questions = new ArrayList<>();
            test.setQuestions(questions);
        }

        if (questionNumber > questions.size()) {
            Question newQuestion = new Question();
            newQuestion.setContent("Untitled");
            newQuestion.setNumberInLesson(questionNumber); // Use the actual question number
            newQuestion.setTest(test);

            Answer defaultAnswer = new Answer();
            defaultAnswer.setIsCorrect(0);
            defaultAnswer.setContent("Answer 1");
            defaultAnswer.setQuestion(newQuestion);

            newQuestion.setAnswers(new ArrayList<>(List.of(defaultAnswer)));
            questions.add(newQuestion);

            testRepository.save(test);
        }

        TestShowPage testShowPage = new TestShowPage();
        testShowPage.setTest(test);
        testShowPage.setCourseId(courseId);
        testShowPage.setQuestionNumber(questionNumber);
        model.addAttribute("page", testShowPage);

        return "contents/test-edit";
    }

    /**
     * Provides editing question
     * @param courseId
     * @param lessonNumber
     * @param questionNumber
     * @param questionContent
     * @param allParams
     * @param correctAnswers
     * @param deletedAnswers
     * @param redirect
     * @return redirect to this question after saving or to next question/lesson
     */
    @PostMapping("/{courseId}/{lessonNumber}/{questionNumber}/edit")
    public String handleEditQuestion(
            @PathVariable Long courseId,
            @PathVariable Integer lessonNumber,
            @PathVariable Integer questionNumber,
            @RequestParam String questionContent,
            @RequestParam Map<String, String> allParams,
            @RequestParam(required = false) List<Long> correctAnswers,
            @RequestParam(required = false) List<Long> deletedAnswers,
            @RequestParam(required = false) String redirect) {


        Test test = testRepository.findByLessonId(lessonRepository.findByCourseIdAndLessonNumber(courseId, lessonNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"))
                .getId())
                .orElseThrow(() -> new ResourceNotFoundException("Test not found"));

        Question question = test.getQuestions().get(questionNumber - 1);

        List<Answer> currentAnswers = new ArrayList<>();
        Set<Long> processedIds = new HashSet<>();

        allParams.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("answer_"))
                .forEach(entry -> {
                    try {
                        String idStr = entry.getKey().substring("answer_".length());
                        Long answerId = Long.parseLong(idStr);
                        String answerContent = entry.getValue();

                        Answer answer;
                        if (answerId > 0) {
                            answer = question.getAnswers().stream()
                                    .filter(a -> a.getId() == answerId)
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Answer not found"));
                            answer.setContent(answerContent);
                        } else {
                            answer = new Answer();
                            answer.setContent(answerContent);
                            answer.setQuestion(question);
                        }
                        answer.setIsCorrect(correctAnswers != null && correctAnswers.contains(answerId) ? 1 : 0);
                        currentAnswers.add(answer);
                        processedIds.add(answerId);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid answer format");
                    }
                });

        if (deletedAnswers != null) {
            deletedAnswers.forEach(id -> {
                if (id > 0) {
                    question.getAnswers().removeIf(a -> a.getId() == id);
                }
            });
        }

        question.setContent(questionContent);
        question.clearAndSetAnswers(currentAnswers);
        testRepository.save(test);

        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }

        return "redirect:/test/" + courseId + "/" + lessonNumber + "/" + questionNumber + "/edit";
    }

    /**
     * Shows test for a user
     * @param courseId
     * @param lessonNum
     * @param questionNumber
     * @param model
     * @param principal
     * @return user test page or redirect to /courses lesson
     */
    @GetMapping("/{courseId}/{lessonNum}/{questionNumber}")
    public String viewTest(@PathVariable Long courseId,
                    @PathVariable int lessonNum,
                    @PathVariable int questionNumber,
                    Model model,
                    Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Lesson lesson = lessonRepository.findByCourseIdAndLessonNumber(courseId, lessonNum)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found"));

        Optional<Test> test = testRepository.findByLessonId(lesson.getId());
        if (user.getCourses().contains(courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found")))) {
            TestShowPage testShowPage = new TestShowPage();

            testShowPage.setTest(test.get());
            testShowPage.setCourseId(courseId);
            testShowPage.setQuestionNumber(questionNumber);

            model.addAttribute("page", testShowPage);
            return "contents/test-user";
        }
        return "redirect:/courses/" + courseId + "/" + lessonNum;
    }

    @PostMapping("/{courseId}/{lessonNumber}/{questionNumber}/delete")
    @Transactional
    public ResponseEntity<?> deleteQuestion(@PathVariable long courseId,
            @PathVariable int lessonNumber,
            @PathVariable int questionNumber,
            Principal principal) {

        try {
            User user = userRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            if (!user.isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Lesson lesson = lessonRepository.findByCourseIdAndLessonNumber(courseId, lessonNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
            Test test = testRepository.findByLessonId(lesson.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Test not found"));
            Question question = questionRepository.findByTestIdAndNumberInLesson(test.getId(), questionNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

            answerRepository.deleteAllByQuestion(question);
            questionRepository.delete(question);

            reorderQuestionsAfterDeletion(test.getId(), questionNumber);

            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error deleting question");
        }
    }

    /**
     * Changes indexation of questions when some question is deleted
     * @param testId
     * @param deletedQuestionNumber
     */
    private void reorderQuestionsAfterDeletion(long testId, int deletedQuestionNumber) {
        List<Question> questionsToReorder = questionRepository
                .findByTestIdAndNumberInLessonGreaterThan(testId, deletedQuestionNumber);

        for (Question q : questionsToReorder) {
            q.setNumberInLesson(q.getNumberInLesson() - 1);
            questionRepository.save(q);
        }
    }
}
