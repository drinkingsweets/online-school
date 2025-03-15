@import app.onlineschool.model.Lesson
@param Lesson currentLesson

@template.layout.sidebar(
content = @`
    </head>
    <body class="bg-gray-50 text-gray-900">

    <main class="p-10 max-w-4xl mx-auto">
        <!-- Header -->
        <header class="mb-6">
            <h1 class="text-3xl font-bold text-gray-900">${currentLesson.getTitle()}</h1>
        </header>

        <!-- Lesson Content -->
        <section class="mb-6">
            <p class="text-gray-700">${currentLesson.getContent()}</p>
        </section>

        <!-- Navigation Buttons -->
        <div class="flex justify-between">
            <form action="/mycourses/${currentLesson.getCourse().getId()}/previous" method="post">
                <button type="submit"
                        class="bg-gray-600 text-white px-6 py-2 rounded-lg hover:bg-gray-700 transition">
                    Back
                </button>
            </form>

            <form action="/mycourses/${currentLesson.getCourse().getId()}/next" method="post">
                <button type="submit"
                        class="bg-orange-600 text-white px-6 py-2 rounded-lg hover:bg-orange-700 transition">
                    Next
                </button>
            </form>
        </div>
    </main>

    </body>
`)
