@param gg.jte.Content content

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online School</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body class="bg-gray-100 text-gray-900 flex">
<!-- Sidebar -->
<div id="sidebar" class="bg-white h-screen p-4 transition-all duration-300 w-16 md:w-64 flex flex-col shadow-lg">
    <button onclick="toggleSidebar()" class="mb-6 self-end">
        <span id="toggleIcon">&#9776;</span>
    </button>
    <nav class="flex-1">
        <ul class="space-y-4">
            <li>
                <a href="/" class="flex items-center space-x-2 p-2 rounded hover:bg-gray-200">
                    <span class="text-xl">🏠</span>
                    <span class="hidden md:inline">Главная</span>
                </a>
            </li>
            <li>
                <a href="/profile" class="flex items-center space-x-2 p-2 rounded hover:bg-gray-200">
                    <span class="text-xl">👤</span>
                    <span class="hidden md:inline">Мой профиль</span>
                </a>
            </li>
            <li>
                <a href="/mycourses" class="flex items-center space-x-2 p-2 rounded hover:bg-gray-200">
                    <span class="text-xl">📚</span>
                    <span class="hidden md:inline">Мои курсы</span>
                </a>
            </li>
        </ul>
    </nav>
    <a href="/logout" class="mt-auto flex items-center space-x-2 p-2 rounded hover:bg-gray-200">
        <span class="text-xl">🚪</span>
        <span class="hidden md:inline">Выход</span>
    </a>
</div>

<!-- Main Content -->
<main class="flex-1 p-10">
    ${content}
</main>


<script>
    function toggleSidebar() {
        const sidebar = document.getElementById('sidebar');
        const schoolTitle = document.getElementById('schoolTitle');
        sidebar.classList.toggle('w-16');
        sidebar.classList.toggle('md:w-64');
        const spans = sidebar.querySelectorAll('span.hidden');
        spans.forEach(span => span.classList.toggle('md:inline'));

        // Show or hide the "Online school" title based on the sidebar width
        if (sidebar.classList.contains('w-16')) {
            schoolTitle.classList.add('hidden');
        } else {
            schoolTitle.classList.remove('hidden');
        }
    }
</script>
</body>
</html>
