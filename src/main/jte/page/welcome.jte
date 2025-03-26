@import app.onlineschool.dto.WelcomePage
@import app.onlineschool.model.Course
@param WelcomePage page

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online School</title>
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
<body class="bg-gray-100 text-gray-900">
<header class="bg-white shadow p-4 flex justify-between items-center">
    <h1 class="text-2xl font-bold">Online School</h1>
    <nav>
        <a href="/login">
            <button>Login</button>
        </a>
        <a href="/register">
            <button>Register</button>
        </a>
    </nav>
</header>

<section class="text-center py-20 bg-gray-200">
    <h2 class="text-4xl font-bold mb-4">Learn Anytime, Anywhere</h2>
    <p class="text-lg text-gray-700 mb-6">Join our online courses and upgrade your skills.</p>
    <a href="/courses">
        <div class="button-container">
            <button class="animated-button">
                <svg viewBox="0 0 24 24" class="arr-2" xmlns="http://www.w3.org/2000/svg">
                    <path d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"></path>
                </svg>
                <span class="text">Explore Courses</span>
                <span class="circle"></span>
                <svg viewBox="0 0 24 24" class="arr-1" xmlns="http://www.w3.org/2000/svg">
                    <path d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"></path>
                </svg>
            </button>
        </div>
    </a>
</section>

<section id="courses" class="py-20 text-center">
    <h2 class="text-3xl font-bold mb-6">Popular Courses</h2>
    <div class="grid md:grid-cols-3 gap-6 px-6">
        @for(Course course: page.getCourses3())
            <a href="/courses/${course.getId()}" class="h-full">
                <div class="bg-white p-6 rounded-lg shadow flex flex-col h-full">
                    <h3 class="text-xl font-semibold">${course.getTitle()}</h3>
                    <p class="text-gray-600 flex-grow">${course.getShortDescription()}</p>
                </div>
            </a>
        @endfor
    </div>
</section>

</body>
</html>
