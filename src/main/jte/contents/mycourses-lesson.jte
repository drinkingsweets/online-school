@import app.onlineschool.model.Lesson
@import app.onlineschool.model.Test
@import app.onlineschool.dto.CurrentLessonPage
@import app.onlineschool.service.MarkdownService
@param MarkdownService markdownService
@param CurrentLessonPage page

@template.layout.sidebar(
content = @`
    </head>
    <body class="bg-gray-50 text-gray-900">

    <main class="p-10 max-w-4xl mx-auto">
        <!-- Header -->
        <header class="mb-6">
            <h1 class="text-3xl font-bold text-gray-900">${page.getLesson().getTitle()}</h1>
        </header>

        <!-- Lesson Content -->
        <section class="mb-6">
            <div class="text-gray-700">$unsafe{(markdownService.convertMarkdownToHtml(page.getLesson().getContent()))}</div>
        </section>

        <!-- Navigation Buttons -->
        <div class="flex justify-between">
            <form action="/mycourses/${page.getLesson().getCourse().getId()}/${page.getLesson().getLessonNumber() - 1}" method="post">
                <button type="submit"
                        class="bg-gray-600 text-white px-6 py-2 rounded-lg hover:bg-gray-700 transition">
                    Back
                </button>
            </form>

            @if(!page.isHasTest())
            <form action="/mycourses/${page.getLesson().getCourse().getId()}/next${page.getLessonNum().isEmpty() ? "":"?lessonNum=" + page.getLessonNum()}" method="post">
                <button type="submit"
                        class="bg-orange-600 text-white px-6 py-2 rounded-lg hover:bg-orange-700 transition">
                    Next
                </button>
            </form>
                @else
                <form action="/test/${page.getLesson().getCourse().getId()}/${page.getLesson().getLessonNumber()}/1" method="get">
                <button type="submit"
                        class="bg-orange-600 text-white px-6 py-2 rounded-lg hover:bg-orange-700 transition">
                    Complete test
                </button>
                @endif
            </form>
        </div>
    </main>

    </body>
`)
