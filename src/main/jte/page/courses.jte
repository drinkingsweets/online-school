@import java.util.List
@import app.onlineschool.model.Course
@param List<Course> courses

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Courses</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body class="bg-gray-100">

<!-- Top Section with Online School Title -->
<header class="bg-orange-600 text-white p-6 text-center">
    <h1 class="text-3xl font-bold">Online School</h1>
</header>

<!-- All Courses Section -->
<main class="p-10">
    <h2 class="text-2xl font-semibold text-gray-800 mb-6">All Courses</h2>
    <div class="grid grid-cols-1 gap-6">
    @for(Course course: courses)
            <a href="/courses/${course.getId()}"
               class="bg-white p-10 rounded-lg shadow hover:bg-gray-50 transition duration-200 w-full">
                <h3 class="text-xl font-semibold text-gray-800">${course.getTitle()}</h3>
                <p class="text-gray-600">${course.getShortDescription()}</p>
            </a>
    @endfor
        </div>
</main>

</body>
</html>
