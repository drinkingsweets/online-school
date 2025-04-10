@import app.onlineschool.model.*
@param app.onlineschool.dto.TestShowPage page

@template.layout.sidebar(content = @`
    <body>
    <div class="container">
        <div class="user-lesson-title">${page.getTest().getLesson().getTitle()}</div>

        <div class="question-counter">
            Question ${page.getQuestionNumber()} of ${page.getTest().getQuestions().size()}
        </div>

        <div class="user-nav-buttons">
            <button class="user-nav-btn" onclick="navigateToQuestion(${page.getQuestionNumber() - 1})"
                    disabled="${page.getQuestionNumber() <= 1 ? true:false}">
                ← Previous Question
            </button>
            <button id="nextQuestionBtn" class="user-nav-btn"
                    onclick="checkAnswerBeforeProceeding()"
                    disabled="${page.getQuestionNumber() >= page.getTest().getQuestions().size() ? true:false}">
                Next Question →
            </button>
        </div>

        <form id="testForm" action="/mycourses/${page.getCourseId()}/next"
              method="post"
              data-course-id="${page.getCourseId()}"
              data-lesson-number="${page.getTest().getLesson().getLessonNumber()}"
              data-question-number="${page.getQuestionNumber()}"
              data-total-questions="${page.getTest().getQuestions().size()}"
              data-question-id="${page.getTest().getQuestions().get(page.getQuestionNumber() - 1).getId()}"
              data-correct-answer="${page.getTest().getQuestions().get(page.getQuestionNumber() - 1).getAnswers().stream().filter(a -> a.getIsCorrect() == 1).findFirst().get().getId()}">
            <div class="question-container">
                <div class="question-text">
                    ${page.getTest().getQuestions().get(page.getQuestionNumber() - 1).getContent()}
                </div>

                @for(Answer answer: page.getTest().getQuestions().get(page.getQuestionNumber() - 1).getAnswers())
                    <label class="answer-option">
                        <input type="radio"
                               name="current_question"
                               value="${answer.getId()}"
                               onchange="enableNextButtonIfCorrect()">
                        ${answer.getContent()}
                    </label>
                @endfor
            </div>

            <div id="feedbackMessage" class="feedback-message"></div>

            @if(page.getQuestionNumber() == page.getTest().getQuestions().size())
                <button type="submit" class="submit-btn" id="submitTestBtn" disabled>
                    Submit Test
                </button>
            @endif


        </form>
    </div>

    @raw
    <script>
        // Store selected answers
        const userAnswers = {};

        function enableNextButtonIfCorrect() {
            const form = document.getElementById('testForm');
            const correctAnswerId = parseInt(form.dataset.correctAnswer);
            const nextQuestionBtn = document.getElementById('nextQuestionBtn');
            const submitTestBtn = document.getElementById('submitTestBtn');
            const feedbackMessage = document.getElementById('feedbackMessage');
            const questionId = form.dataset.questionId;

            const selectedOption = document.querySelector('input[name="current_question"]:checked');

            if (selectedOption) {
                const selectedAnswerId = parseInt(selectedOption.value);
                const isCorrect = selectedAnswerId === correctAnswerId;

                // Store the answer
                userAnswers[questionId] = {
                    answerId: selectedAnswerId,
                    isCorrect: isCorrect
                };

                if (isCorrect) {
                    feedbackMessage.textContent = "Correct! You may proceed.";
                    feedbackMessage.style.color = "green";
                    nextQuestionBtn.disabled = false;
                    if (submitTestBtn) submitTestBtn.disabled = false;
                } else {
                    feedbackMessage.textContent = "Incorrect. Please try again.";
                    feedbackMessage.style.color = "red";
                    nextQuestionBtn.disabled = true;
                    if (submitTestBtn) submitTestBtn.disabled = true;
                }
            }
        }

        function checkAnswerBeforeProceeding() {
            const form = document.getElementById('testForm');
            const questionId = form.dataset.questionId;
            const questionNumber = parseInt(form.dataset.questionNumber);
            const totalQuestions = parseInt(form.dataset.totalQuestions);

            if (userAnswers[questionId] && userAnswers[questionId].isCorrect) {
                const nextQuestionNumber = questionNumber + 1;
                if (nextQuestionNumber <= totalQuestions) {
                    window.location.href = `/test/${form.dataset.courseId}/${form.dataset.lessonNumber}/${nextQuestionNumber}`;
                }
            } else {
                alert("Please answer the current question correctly before proceeding.");
            }
        }

        function navigateToQuestion(questionNumber) {
            const form = document.getElementById('testForm');
            window.location.href = `/test/${form.dataset.courseId}/${form.dataset.lessonNumber}/${questionNumber}`;
        }
    </script>
    @endraw
`)