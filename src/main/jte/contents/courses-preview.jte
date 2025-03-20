@import app.onlineschool.dto.PreviewPage
@param PreviewPage page

@template.layout.sidebar(content =
@`
<body class="bg-gray-100 flex justify-center items-center min-h-screen p-4">
    <div class="bg-white shadow-lg w-full h-screen flex flex-col p-6">
        <div class="flex flex-col space-y-4">
            <h1 class="text-3xl font-semibold">${page.getLesson().getTitle()}</h1>
            <div class="text-gray-700">
                $unsafe{page.getMarkdownService().convertMarkdownToHtml(page.getLesson().getContent())}
            </div>
        </div>
        <div class="flex justify-between mt-4">
            <a href="/courses/${page.getLesson().getCourse().getId()}/${page.getLesson().getLessonNumber()}/edit" class="bg-gray-600 text-white px-6 py-2 rounded-lg hover:bg-gray-700 transition">
                Edit Lesson
            </a>
            <a href="/courses/${page.getLesson().getCourse().getId()}" class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition">
                Back to Course
            </a>
        </div>
    </div>
</body>`)