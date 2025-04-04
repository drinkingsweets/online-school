@import app.onlineschool.model.*
@param app.onlineschool.dto.TestShowPage page

@template.layout.sidebar(content = @`
<%--    <!DOCTYPE html>--%>
<%--    <html lang="en">--%>
<%--    <head>--%>
<%--        <meta charset="UTF-8">--%>
<%--        <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--        <title>Question ${page.getQuestionNumber()}</title>--%>
<%--        <style>--%>
<%--            /*body {*/--%>
<%--            /*    font-family: 'Segoe UI', sans-serif;*/--%>
<%--            /*    padding: 0;*/--%>
<%--            /*    margin: 0;*/--%>
<%--            /*    background-color: #f8f9fa;*/--%>
<%--            /*}*/--%>

<%--            /*.container {*/--%>
<%--            /*    max-width: 100%;*/--%>
<%--            /*    padding: 20px;*/--%>
<%--            /*}*/--%>

<%--            /*.lesson-title {*/--%>
<%--            /*    font-size: 1.2rem;*/--%>
<%--            /*    font-weight: 600;*/--%>
<%--            /*    color: #333;*/--%>
<%--            /*    margin-bottom: 60px;*/--%>
<%--            /*    padding-bottom: 8px;*/--%>
<%--            /*    border-bottom: 1px solid rgba(255, 165, 0, 0.3);*/--%>
<%--            /*}*/--%>

<%--            /*.question-number {*/--%>
<%--            /*    font-size: 1.1rem;*/--%>
<%--            /*    font-weight: 500;*/--%>
<%--            /*    color: #333;*/--%>
<%--            /*    margin-bottom: 20px;*/--%>
<%--            /*    padding-bottom: 8px;*/--%>
<%--            /*    border-bottom: 1px solid rgba(255, 165, 0, 0.3);*/--%>
<%--            /*}*/--%>

<%--            /*.minimal-input {*/--%>
<%--            /*    width: 100%;*/--%>
<%--            /*    padding: 12px 0;*/--%>
<%--            /*    border: none;*/--%>
<%--            /*    border-bottom: 1px solid rgba(255, 165, 0, 0.5);*/--%>
<%--            /*    background: transparent;*/--%>
<%--            /*    outline: none;*/--%>
<%--            /*    font-size: 1rem;*/--%>
<%--            /*    margin-bottom: 20px;*/--%>
<%--            /*    transition: border 0.2s ease;*/--%>
<%--            /*}*/--%>

<%--            /*.minimal-input:focus {*/--%>
<%--            /*    border-bottom: 2px solid darkorange;*/--%>
<%--            /*}*/--%>

<%--            /*.answer-row {*/--%>
<%--            /*    display: flex;*/--%>
<%--            /*    align-items: center;*/--%>
<%--            /*    width: 100%;*/--%>
<%--            /*    margin-bottom: 15px;*/--%>
<%--            /*}*/--%>

<%--            /*.answer-input {*/--%>
<%--            /*    flex-grow: 1;*/--%>
<%--            /*    padding: 10px 0;*/--%>
<%--            /*    border: none;*/--%>
<%--            /*    border-bottom: 1px solid rgba(255, 165, 0, 0.3);*/--%>
<%--            /*    background: transparent;*/--%>
<%--            /*    outline: none;*/--%>
<%--            /*    margin-right: 15px;*/--%>
<%--            /*}*/--%>

<%--            /*.correct-checkbox {*/--%>
<%--            /*    margin-left: 10px;*/--%>
<%--            /*    width: 20px;*/--%>
<%--            /*    height: 20px;*/--%>
<%--            /*    accent-color: darkorange;*/--%>
<%--            /*}*/--%>

<%--            /*.btn-container {*/--%>
<%--            /*    display: flex;*/--%>
<%--            /*    justify-content: space-between;*/--%>
<%--            /*    margin-top: 30px;*/--%>
<%--            /*    gap: 10px;*/--%>
<%--            /*}*/--%>

<%--            /*.btn-group {*/--%>
<%--            /*    display: flex;*/--%>
<%--            /*    gap: 10px;*/--%>
<%--            /*}*/--%>

<%--            /*.simple-btn {*/--%>
<%--            /*    padding: 10px 20px;*/--%>
<%--            /*    background: transparent;*/--%>
<%--            /*    border: 1px solid darkorange;*/--%>
<%--            /*    color: darkorange;*/--%>
<%--            /*    border-radius: 4px;*/--%>
<%--            /*    cursor: pointer;*/--%>
<%--            /*    font-size: 0.95rem;*/--%>
<%--            /*    transition: all 0.2s ease;*/--%>
<%--            /*    white-space: nowrap;*/--%>
<%--            /*}*/--%>

<%--            /*.simple-btn:hover {*/--%>
<%--            /*    background: rgba(255, 140, 0, 0.1);*/--%>
<%--            /*}*/--%>

<%--            /*.save-btn {*/--%>
<%--            /*    background: darkorange;*/--%>
<%--            /*    color: white;*/--%>
<%--            /*    border: none;*/--%>
<%--            /*}*/--%>

<%--            /*.save-btn:hover {*/--%>
<%--            /*    background: #ff8c00;*/--%>
<%--            /*}*/--%>

<%--            /*.nav-buttons {*/--%>
<%--            /*    display: flex;*/--%>
<%--            /*    justify-content: space-between;*/--%>
<%--            /*    margin-bottom: 20px;*/--%>
<%--            /*}*/--%>

<%--            /*.nav-btn {*/--%>
<%--            /*    padding: 8px 8px;*/--%>
<%--            /*    background: transparent;*/--%>
<%--            /*    border: 1px solid darkorange;*/--%>
<%--            /*    color: darkorange;*/--%>
<%--            /*    border-radius: 4px;*/--%>
<%--            /*    cursor: pointer;*/--%>
<%--            /*    font-size: 0.9rem;*/--%>
<%--            /*    transition: all 0.2s ease;*/--%>
<%--            /*}*/--%>

<%--            /*.nav-btn:hover {*/--%>
<%--            /*    background: rgba(255, 140, 0, 0.1);*/--%>
<%--            /*}*/--%>

<%--            /*.nav-btn:disabled {*/--%>
<%--            /*    opacity: 0.5;*/--%>
<%--            /*    cursor: not-allowed;*/--%>
<%--            /*}*/--%>

<%--            /*.answer-actions {*/--%>
<%--            /*    display: flex;*/--%>
<%--            /*    align-items: center;*/--%>
<%--            /*    gap: 10px;*/--%>
<%--            /*    margin-left: 10px;*/--%>
<%--            /*}*/--%>

<%--            /*.delete-btn {*/--%>
<%--            /*    color: #ff4444;*/--%>
<%--            /*    background: none;*/--%>
<%--            /*    border: none;*/--%>
<%--            /*    cursor: pointer;*/--%>
<%--            /*    font-size: 0.9rem;*/--%>
<%--            /*    padding: 4px 8px;*/--%>
<%--            /*    border-radius: 4px;*/--%>
<%--            /*    transition: background-color 0.2s;*/--%>
<%--            /*}*/--%>

<%--            /*.delete-btn:hover {*/--%>
<%--            /*    background-color: rgba(255, 68, 68, 0.1);*/--%>
<%--            /*}*/--%>
<%--        </style>--%>
<%--    </head>--%>
    <body>
    <div class="container">
        <div class="nav-buttons">
            <button class="nav-btn" onclick="navigateToQuestion(${page.getQuestionNumber() - 1})"
                    disabled="${page.getQuestionNumber() <= 1 ? true:false}">
                ← Previous Question
            </button>
            <div class="lesson-title">Question №${page.getQuestionNumber()}</div>
            <button class="nav-btn" onclick="navigateToQuestion(${page.getQuestionNumber() + 1})"
                    disabled="${page.getQuestionNumber() >= page.getTest().getQuestions().size() ? true:false}">
                Next Question →
            </button>
        </div>

        <form id="questionForm"
              action="/test/${page.getCourseId()}/${page.getTest().getLesson().getLessonNumber()}/${page.getQuestionNumber()}/edit"
              method="post"
              data-course-id="${page.getCourseId()}"
              data-lesson-number="${page.getTest().getLesson().getLessonNumber()}"
              data-question-number="${page.getQuestionNumber()}"
              data-total-questions="${page.getTest().getQuestions().size()}">
            <input type="text" id="questionContent" name="questionContent"
                   class="minimal-input"
                   value="${page.getTest().getQuestions().get(page.getQuestionNumber() - 1).getContent()}"
                   placeholder="Question text" required>

            <div id="answers-container">
                @for(Answer answer: page.getTest().getQuestions().get(page.getQuestionNumber() - 1).getAnswers())
                    <div class="answer-row" id="answer-row-${answer.getId()}">
                        <input type="text" name="answer_${answer.getId()}"
                               class="answer-input"
                               value="${answer.getContent()}"
                               placeholder="Answer option" required>
                        <div class="answer-actions">
                            <input type="checkbox" id="correct_${answer.getId()}"
                                   class="correct-checkbox"
                                   name="correctAnswers" value="${answer.getId()}"
                                     checked="${answer.getIsCorrect() == 1 ? true:false}">
                            <label for="correct_${answer.getId()}">Correct</label>
                            <button type="button" class="delete-btn"
                                    onclick="deleteAnswer(${answer.getId()})"
                                    title="Delete answer">
                                ✕
                            </button>
                            <input type="hidden" name="existingAnswers" value="${answer.getId()}">
                        </div>
                    </div>
                @endfor
            </div>

            <div class="btn-container">
                <div class="btn-group">
                    <button type="submit" class="simple-btn save-btn">
                        Save question
                    </button>
                    <button type="button" class="simple-btn" onclick="saveAndExit()">
                        Save & Exit
                    </button>
                    <button type="button" class="simple-btn" onclick="saveAndAddNext()">
                        Save & Add Next
                    </button>
                </div>

                <button type="button" class="simple-btn" onclick="addAnswer()">
                    + Add answer
                </button>
            </div>
        </form>
    </div>

    @raw
    <script>
        function deleteAnswer(answerId) {
        if (confirm('Are you sure you want to delete this answer?')) {
            const answerRow = document.getElementById(`answer-row-${answerId}`);
            if (answerRow) {
                // For existing answers (positive IDs), add a deleted marker
                if (answerId > 0) {
                    const deletedInput = document.createElement('input');
                    deletedInput.type = 'hidden';
                    deletedInput.name = 'deletedAnswers';
                    deletedInput.value = answerId;
                    document.getElementById('questionForm').appendChild(deletedInput);
                }
                answerRow.remove();
            }
        }
    }

        function addAnswer() {
            const answersContainer = document.getElementById('answers-container');
            const newId = -Date.now();

            const answerRow = document.createElement('div');
            answerRow.className = 'answer-row';
            answerRow.id = `answer-row-${newId}`;

            const inputField = document.createElement('input');
            inputField.type = 'text';
            inputField.className = 'answer-input';
            inputField.name = `answer_${newId}`;
            inputField.required = true;
            inputField.placeholder = 'Answer option';

            const actionsDiv = document.createElement('div');
            actionsDiv.className = 'answer-actions';

            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.className = 'correct-checkbox';
            checkbox.id = `correct_${newId}`;
            checkbox.name = 'correctAnswers';
            checkbox.value = newId;

            const checkboxLabel = document.createElement('label');
            checkboxLabel.htmlFor = `correct_${newId}`;
            checkboxLabel.textContent = 'Correct';

            const deleteBtn = document.createElement('button');
            deleteBtn.type = 'button';
            deleteBtn.className = 'delete-btn';
            deleteBtn.textContent = '✕';
            deleteBtn.title = 'Delete answer';
            deleteBtn.onclick = function () {
                deleteAnswer(newId);
            };

            actionsDiv.appendChild(checkbox);
            actionsDiv.appendChild(checkboxLabel);
            actionsDiv.appendChild(deleteBtn);

            answerRow.appendChild(inputField);
            answerRow.appendChild(actionsDiv);

            answersContainer.appendChild(answerRow);
            inputField.focus();
        }

        // JavaScript functions remain unchanged
        function navigateToQuestion(questionNumber) {
            const form = document.getElementById('questionForm');
            const courseId = form.dataset.courseId;
            const lessonNumber = form.dataset.lessonNumber;
            const totalQuestions = parseInt(form.dataset.totalQuestions);


            if (questionNumber < 1 || questionNumber > totalQuestions) {
                return;
            }
            window.location.href = `/test/${courseId}/${lessonNumber}/${questionNumber}/edit`;
        }

        // Other functions remain the same

        function saveAndExit() {
            const form = document.getElementById('questionForm');
            form.action = `/test/${form.dataset.courseId}/${form.dataset.lessonNumber}/${form.dataset.questionNumber}/edit?redirect=/courses/${form.dataset.courseId}/${form.dataset.lessonNumber}/edit`;
            form.submit();
        }

        function saveAndAddNext() {
            const form = document.getElementById('questionForm');
            const nextQuestionNumber = parseInt(form.dataset.questionNumber) + 1;
            form.action = `/test/${form.dataset.courseId}/${form.dataset.lessonNumber}/${form.dataset.questionNumber}/edit?redirect=/test/${form.dataset.courseId}/${form.dataset.lessonNumber}/${nextQuestionNumber}/edit`;
            form.submit();
        }
    </script>
    @endraw
    </body>
<%--    </html>--%>
`)