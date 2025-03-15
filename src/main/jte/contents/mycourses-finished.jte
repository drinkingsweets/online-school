@import app.onlineschool.model.Course
@param Course course

@template.layout.sidebar(
content = @`
    <body class="bg-gray-50 text-gray-900">

    <main class="p-10 max-w-4xl mx-auto">
        <!-- Header -->
        <header class="mb-6">
            <h1 class="text-3xl font-bold text-gray-900">Congratulations!</h1>
        </header>

        <!-- Finished Content -->
        <section class="mb-6">
            <p class="text-gray-700">You have successfully completed the course: <strong>${course.getTitle()}</strong>.
            </p>
            <p class="text-gray-700">We hope you enjoyed the lessons and found them valuable.</p>
        </section>

        <!-- Back to Courses Button -->
        <div class="flex justify-end">
            <a href="/mycourses" class="bg-orange-600 text-white px-6 py-2 rounded-lg hover:bg-orange-700 transition">
                Back to My Courses
            </a>
        </div>
    </main>

    </body>`
)