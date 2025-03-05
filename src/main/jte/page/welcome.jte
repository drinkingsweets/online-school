<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online School</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body class="bg-gray-100 text-gray-900">

    <header class="bg-white shadow p-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">Online School</h1>
        <nav>
            <a href="/login"><button>Login</button></a>
            <a href="/register"><button>Register</button></a>
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
            <div class="bg-white p-6 rounded-lg shadow">
                <h3 class="text-xl font-semibold">Web Development</h3>
                <p class="text-gray-600">Learn HTML, CSS, and JavaScript from scratch.</p>
            </div>
            <div class="bg-white p-6 rounded-lg shadow">
                <h3 class="text-xl font-semibold">Data Science</h3>
                <p class="text-gray-600">Master Python, SQL, and Machine Learning.</p>
            </div>
            <div class="bg-white p-6 rounded-lg shadow">
                <h3 class="text-xl font-semibold">Digital Marketing</h3>
                <p class="text-gray-600">Boost your brand with SEO and social media.</p>
            </div>
        </div>
    </section>
</body>
</html>
