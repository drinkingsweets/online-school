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
<body class="bg-gray-100">

<!-- Top Section with Online School Title -->
<header class="bg-orange-600 text-white p-6 text-center">
    <h1 class="text-3xl font-bold">Online School</h1>
</header>

<!-- Search Section -->
<section class="p-10">
    <form action="/courses" method="post" class="flex items-center space-x-2 mb-0">
        <label for="startDate" class="text-gray-700">Start Date:</label>
        <input type="date" id="startDate" name="startDate" class="p-2 border border-gray-300 rounded-lg">
        <label for="endDate" class="text-gray-700">End Date:</label>
        <input type="date" id="endDate" name="endDate" class="p-2 border border-gray-300 rounded-lg">
        <input type="text" name="query" placeholder="Search for courses..." class="p-2 border border-gray-300 rounded-lg flex-grow">
        <button type="submit" class="bg-orange-600 text-white px-4 py-2 rounded-lg hover:bg-orange-700 transition">Search</button>
    </form>
</section>
<!-- All Courses Section -->
<main class="p-10">
    <h2 class="text-2xl font-semibold text-gray-800 mb-4">All Courses</h2>
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