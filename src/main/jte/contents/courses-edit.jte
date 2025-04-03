@import app.onlineschool.model.Lesson
@import app.onlineschool.dto.CourseEditPage
@param CourseEditPage page

@template.layout.sidebar(content =
@`
<body class="bg-gray-100 flex justify-center items-center min-h-screen p-4">
    <div class="bg-white shadow-lg w-full h-screen flex flex-col p-6">
        <form action="/courses/${page.getLesson().getCourse().getId()}/${page.getLesson().getLessonNumber()}/edit" method="post" class="flex flex-col space-y-4 flex-grow">
            <input type="text" id="title" name="title" value="${page.getLesson().getTitle()}" required
                   class="w-full text-3xl font-semibold outline-none border-transparent bg-transparent focus:bg-gray-100 p-2">
            <textarea name="content" required
                      class="w-full h-full text-lg outline-none border-transparent bg-transparent focus:bg-gray-100 focus:ring-0 transition p-2 resize-none">${page.getLesson().getContent()}</textarea>
            <div class="flex justify-between">
                <div class="flex flex-col space-y-4">
                    @if (page.getPreviousLesson() != 0)
                    <a href="/courses/${page.getLesson().getCourse().getId()}/${page.getPreviousLesson()}/edit"
                       class="bg-gray-600 text-white px-6 py-2 rounded-lg hover:bg-gray-700 transition">
                        Previous Lesson
                    </a>
                    @endif
                    <a href="/test/${page.getLesson().getCourse().getId()}/${page.getLesson().getLessonNumber()}/create" class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition flex items-center justify-center">
                        View Test
                    </a>

                </div>
                <div class="flex flex-col space-y-4">
                    <a href="/courses/${page.getLesson().getCourse().getId()}/${page.getNextLesson()}/edit" class="bg-orange-600 text-white px-6 py-2 rounded-lg hover:bg-orange-700 transition">
                        Next Lesson
                    </a>
                    <button type="submit" class="bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700 transition">
                        Save & Preview
                    </button>
                </div>
            </div>
        </form>
    </div>
</body>`)