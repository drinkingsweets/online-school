@import app.onlineschool.model.Course
@import app.onlineschool.model.Lesson
@param Course course
@param boolean isAdded
@param boolean isAdmin

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${course.getTitle()} - Course Info</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link rel="apple-touch-icon" sizes="57x57" href="/favicon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="/favicon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/favicon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="/favicon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/favicon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="/favicon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/favicon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="/favicon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="/favicon-180x180.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="192x192" href="/favicon-192x192.png">
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
    <link rel="icon" type="image/x-icon" href="/favicon.ico">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/favicon-144x144.png">
    <meta name="msapplication-config" content="/browserconfig.xml">
</head>
<body class="bg-gray-50 text-gray-900">

<main class="p-10 max-w-4xl mx-auto">
    <!-- Header -->
    <header class="mb-6">
        <h1 class="text-3xl font-bold text-gray-900">${course.getTitle()}</h1>
    </header>

    <!-- Short Description -->
    <section class="mb-6">
        <p class="text-gray-700">${course.getShortDescription()}</p>
    </section>

    <div class="flex justify-between items-start gap-10">
        <!-- Add Course Button or Added Button -->
        @if (!isAdded)
        <form action="/courses/${course.getId()}/add" method="post">
            <input type="hidden" name="courseId" value="${course.getId()}"/>
            <button type="submit" class="bg-orange-600 text-white px-6 py-2 rounded-lg hover:bg-orange-700 transition">
                Add Course
            </button>
        </form>
        @else
        <button class="bg-orange-200 text-gray-700 px-6 py-2 rounded-lg cursor-not-allowed">
            Added
        </button>
        @endif
        @if (isAdmin)
        <form action="/courses/${course.getId()}/${course.getLessons()
                             .stream()
                             .mapToInt(Lesson::getLessonNumber)
                             .max()
                             .orElse(1)}/edit" method="get">
            <input type="hidden" name="courseId" value="${course.getId()}"/>
            <button type="submit" class="bg-orange-600 text-white px-6 py-2 rounded-lg hover:bg-orange-700 transition">
                Edit course
            </button>
        </form>
            <form action="/courses/${course.getId()}/delete"
                  method="post"
                  class="absolute top-6 right-6">
                <button type="submit"
                        class="bg-red-600 text-white px-3 py-2 rounded-lg hover:bg-red-700 transition flex items-center justify-center"
                        onclick="return confirm('Are you sure you want to delete this lesson?')"
                        title="Delete">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                        <path fill-rule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clip-rule="evenodd"/>
                    </svg>
                </button>
            </form>
        @endif

        <!-- Course Plan -->
        <aside class="w-1/3 bg-gray-100 p-5 rounded-lg shadow">
            <h2 class="text-lg font-semibold text-gray-900 mb-4">Course Plan</h2>
            <ol class="list-decimal list-inside space-y-2">
                @for(Lesson lesson: course.getLessons())
                <li class="bg-white p-3 rounded shadow-sm">${lesson.getTitle()}</li>
                @endfor
            </ol>
        </aside>
    </div>
</main>

</body>
</html>