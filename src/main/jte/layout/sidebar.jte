@param gg.jte.Content content

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online School</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github-dark.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/java.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/python.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/sql.min.js"></script>
    <script>hljs.highlightAll();</script>
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
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="icon" type="image/png" sizes="96x96" href="/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="192x192" href="/favicon-192x192.png">
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
    <link rel="icon" type="image/x-icon" href="/favicon.ico">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/favicon-144x144.png">
    <meta name="msapplication-config" content="/browserconfig.xml">

</head>
<body class="bg-gray-100 text-gray-900 flex">
<!-- Sidebar -->
<div id="sidebar"
     class="bg-white h-screen min-h-screen p-4 transition-all duration-300 w-16 flex flex-col shadow-lg fixed top-0 left-0 z-50">
    <button onclick="toggleSidebar()" class="mb-6 self-end">
        <span id="toggleIcon">&#9776;</span>
    </button>
    <nav class="flex-1">
        <ul class="space-y-4">
            <li>
                <a href="/home" class="flex items-center space-x-2 p-2 rounded hover:bg-gray-200">
                    <span class="text-xl">🏠</span>
                    <span class="text-xs hidden">Главная</span>
                </a>
            </li>
            <li>
                <a href="/profile" class="flex items-center space-x-2 p-2 rounded hover:bg-gray-200">
                    <span class="text-xl">👤</span>
                    <span class="text-xs hidden">Мой профиль</span>
                </a>
            </li>
            <li>
                <a href="/mycourses" class="flex items-center space-x-2 p-2 rounded hover:bg-gray-200">
                    <span class="text-xl">📚</span>
                    <span class="text-xs hidden">Мои курсы</span>
                </a>
            </li>
        </ul>
    </nav>
    <a href="/about" class="mt-auto flex items-center space-x-2 p-2 rounded hover:bg-gray-200">
        <span class="text-xl">ℹ️</span>
        <span class="text-xs hidden">Об авторе</span>
    </a>

    <a href="/logout" class="mt-auto flex items-center space-x-2 p-2 rounded hover:bg-gray-200">
        <span class="text-xl">🚪</span>
        <span class="text-xs hidden">Выход</span>
    </a>
</div>

<!-- Main Content -->
<div id="mainContent" class="flex-1 ml-16 p-4 transition-all duration-300">
    ${content}
</div>

<script>
    function toggleSidebar() {
        const sidebar = document.getElementById('sidebar');
        const mainContent = document.getElementById('mainContent');
        const spans = sidebar.querySelectorAll('nav a span.text-xs');

        // Determine current state: if sidebar is collapsed it has class w-16
        const isCollapsed = sidebar.classList.contains('w-16');

        // Toggle sidebar and main content width classes
        sidebar.classList.toggle('w-16');
        sidebar.classList.toggle('w-64');
        mainContent.classList.toggle('ml-16');
        mainContent.classList.toggle('ml-64');

        if (isCollapsed) {
            // If expanding, wait for transition end on width property then show text
            sidebar.addEventListener('transitionend', function handler(e) {
                if (e.propertyName === 'width') {
                    spans.forEach(span => {
                        span.classList.remove('hidden');
                    });
                    sidebar.removeEventListener('transitionend', handler);
                }
            }, {once: true});
        } else {
            // If collapsing, hide text immediately
            spans.forEach(span => {
                span.classList.add('hidden');
            });
        }
    }
</script>
</body>
</html>