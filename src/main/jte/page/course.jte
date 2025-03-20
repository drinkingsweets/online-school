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