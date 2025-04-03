package app.onlineschool.controller;

import app.onlineschool.dto.TestShowPage;
import app.onlineschool.model.Answer;
import app.onlineschool.model.Lesson;
import app.onlineschool.model.Question;
import app.onlineschool.model.Test;
import app.onlineschool.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @GetMapping("/{courseId}/{lessonNum}/create")
    String index(@PathVariable Long courseId, @PathVariable int lessonNum, Principal principal) {

        if (userRepository.findByUsername(principal.getName()).get().getRole() == 0) {
            return "redirect:/courses/" + courseId + "/" + lessonNum;
        }

        Lesson lesson = lessonRepository.findByCourseIdAndLessonNumber(courseId, lessonNum).get();
        Optional<Test> test = testRepository.findByLessonId(lesson.getId());
        if (test.isEmpty()) {
            Test newTest = new Test();
            System.out.println(lesson);
            newTest.setLesson(lesson);

            Question newQuestion = new Question();
            newQuestion.setContent("Untitled");
            newQuestion.setNumberInLesson(1);
            newQuestion.setTest(newTest);

            Answer newAnswer = new Answer();
            newAnswer.setIsCorrect(0);
            newAnswer.setContent("Answer 1");
            newAnswer.setQuestion(newQuestion);

            newQuestion.setAnswers(List.of(newAnswer));
            newTest.setQuestions(List.of(newQuestion));

            testRepository.save(newTest);

        }

        return "redirect:/test/" + courseId + "/" + lessonNum + "/1/edit";
    }

    @GetMapping("/{courseId}/{lessonNum}/{questionNumber}/edit")
    String viewEdit(@PathVariable Long courseId, @PathVariable int lessonNum, @PathVariable Integer questionNumber, Model model, Principal principal) {
        if (userRepository.findByUsername(principal.getName()).get().getRole() == 0) {
            return "redirect:/course/" + courseId + "/" + lessonNum;
        }
        Lesson lesson = lessonRepository.findByCourseIdAndLessonNumber(courseId, lessonNum).get();
        Optional<Test> test = testRepository.findByLessonId(lesson.getId());
        if (test.isEmpty()) {
            return "redirect:/course/" + courseId + "/" + lessonNum;
        }
        TestShowPage testShowPage = new TestShowPage();
        testShowPage.setTest(test.get());
        testShowPage.setCourseId(courseId);
        testShowPage.setQuestionNumber(questionNumber);
        model.addAttribute("page", testShowPage);
        return "contents/test-edit";
    }

    @PostMapping("/{courseId}/{lessonNumber}/{questionNumber}/edit")
    public String handleEditQuestion(
            @PathVariable Long courseId,
            @PathVariable Integer lessonNumber,
            @PathVariable Integer questionNumber,
            @RequestParam String questionContent,
            @RequestParam Map<String, String> allParams,
            @RequestParam(required = false) List<Long> correctAnswers,
            @RequestParam(required = false) String redirect) {

        // 1. Get the test and question
        Test test = testRepository.findByLessonId(lessonRepository.findByCourseIdAndLessonNumber(courseId, lessonNumber).get().getId()).get();
        Question question = test.getQuestions().get(questionNumber - 1);

        // 2. Update question content
        question.setContent(questionContent);

        // 3. Process answers
        List<Answer> answers = question.getAnswers();
        Map<Long, Answer> answerMap = answers.stream()
                .collect(Collectors.toMap(Answer::getId, Function.identity()));

        allParams.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("answer_"))
                .forEach(entry -> {
                    try {
                        String idStr = entry.getKey().substring("answer_".length());
                        Long answerId = Long.parseLong(idStr);
                        String answerContent = entry.getValue();

                        // Handle null check properly
                        if (answerId != null && answerId > 0 && answerMap.containsKey(answerId)) {
                            // Update existing answer
                            Answer answer = answerMap.get(answerId);
                            answer.setContent(answerContent);
                            // Handle null check for correctAnswers
                            answer.setIsCorrect((correctAnswers != null && correctAnswers.contains(answerId)) ? 1 : 0);
                        } else {
                            // Handle new answers
                            Answer newAnswer = new Answer();
                            newAnswer.setContent(answerContent);
                            // Handle null check for correctAnswers
                            newAnswer.setIsCorrect((correctAnswers != null && correctAnswers.contains(answerId)) ? 1 : 0);
                            newAnswer.setQuestion(question);
                            answers.add(newAnswer);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid answer ID format: " + entry.getKey());
                    }
                });

        // 4. Save changes
        testRepository.save(test);

        // 5. Handle redirect
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }

        return "redirect:/test/" + courseId + "/" + lessonNumber + "/" + questionNumber + "/edit";
    }


}
