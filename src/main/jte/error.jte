@param int status
@param String message

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
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
    <title>Error</title>
    <style>
        @import url(https://fonts.googleapis.com/css2?family=Lato&display=swap);
        @import url(https://fonts.googleapis.com/css2?family=Open+Sans&display=swap);
        @import url(https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200);
    </style>
</head>
<body>
<div id="webcrumbs">
    <div class="max-w bg-white font-sans">
        <main class="flex flex-col items-center justify-center py-20 px-6 text-center">
            <div class="mb-8 transform hover:scale-105 transition-transform duration-300">
                <svg
                        class="w-32 h-32 mx-auto text-orange-500"
                        viewBox="0 0 24 24"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                >
                    <path d="M12 8L12 13" stroke="currentColor" stroke-width="2" stroke-linecap="round"></path>
                    <path
                            d="M12 16.01L12.01 15.999"
                            stroke="currentColor"
                            stroke-width="2"
                            stroke-linecap="round"
                    ></path>
                    <path
                            d="M3.5 12C3.5 7.30558 7.30558 3.5 12 3.5C16.6944 3.5 20.5 7.30558 20.5 12C20.5 16.6944 16.6944 20.5 12 20.5C7.30558 20.5 3.5 16.6944 3.5 12Z"
                            stroke="currentColor"
                            stroke-width="2"
                    ></path>
                </svg>
            </div>
            <h1 class="text-6xl font-bold mb-4 text-gray-800">
                @for(int i = 0; i < Integer.toString(status).length(); i++)
                    <span class="text-orange-500">${Integer.toString(status).charAt(i)}</span>
                @endfor
            </h1>
            <h2 class="text-3xl font-semibold mb-6 text-gray-700">${message}</h2>
            <p class="text-lg text-gray-600 max-w-2xl mb-10">
                The page you&#x27;re looking for doesn&#x27;t exist or has been moved. Let&#x27;s get you back
                to your learning journey.
            </p>
            <div class="flex flex-col sm:flex-row gap-4 justify-center">
                <a
                        href="/home"
                        class="bg-orange-500 hover:bg-orange-600 text-white px-8 py-3 rounded-lg font-medium transform hover:-translate-y-1 transition-all duration-300 shadow-lg hover:shadow-orange-200 flex items-center justify-center gap-2"
                >
                    <span class="material-symbols-outlined">home</span> Back to Homepage
                </a>
                <a
                        href="/courses"
                        class="bg-white hover:bg-gray-50 text-orange-500 border-2 border-orange-500 px-8 py-3 rounded-lg font-medium transform hover:-translate-y-1 transition-all duration-300 shadow-lg hover:shadow-orange-100 flex items-center justify-center gap-2"
                >
                    <span class="material-symbols-outlined">school</span> Browse Courses
                </a>
            </div>
            <div class="mt-16 grid grid-cols-1 sm:grid-cols-3 gap-6 max-w-4xl">
                <a href="/us">
                <div
                        class="bg-orange-50 p-6 rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300 transform hover:-translate-y-1"
                >
                    <span class="material-symbols-outlined text-4xl text-orange-500 mb-3">support_agent</span>
                    <h3 class="text-lg font-semibold mb-2 text-gray-800">Need Help?</h3>
                    <p class="text-gray-600">Contact our support team for assistance</p>
                </div>
                </a>
                <div
                        class="bg-orange-50 p-6 rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300 transform hover:-translate-y-1"
                >
                    <span class="material-symbols-outlined text-4xl text-orange-500 mb-3">search</span>
                    <h3 class="text-lg font-semibold mb-2 text-gray-800">Search Courses</h3>
                    <p class="text-gray-600">Find the perfect course for your needs</p>
                </div>
                <div
                        class="bg-orange-50 p-6 rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300 transform hover:-translate-y-1"
                >
                    <span class="material-symbols-outlined text-4xl text-orange-500 mb-3">forum</span>
                    <h3 class="text-lg font-semibold mb-2 text-gray-800">Community</h3>
                    <p class="text-gray-600">Join our student community forum</p>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.tailwindcss.com"></script>
<script>
    tailwind.config = {
        content: ["./src/**/*.{html,js}"],
        theme: {
            name: "Bluewave",
            fontFamily: {
                sans: [
                    "Open Sans",
                    "ui-sans-serif",
                    "system-ui",
                    "sans-serif",
                    '"Apple Color Emoji"',
                    '"Segoe UI Emoji"',
                    '"Segoe UI Symbol"',
                    '"Noto Color Emoji"'
                ]
            },
            extend: {
                fontFamily: {
                    title: [
                        "Lato",
                        "ui-sans-serif",
                        "system-ui",
                        "sans-serif",
                        '"Apple Color Emoji"',
                        '"Segoe UI Emoji"',
                        '"Segoe UI Symbol"',
                        '"Noto Color Emoji"'
                    ],
                    body: [
                        "Open Sans",
                        "ui-sans-serif",
                        "system-ui",
                        "sans-serif",
                        '"Apple Color Emoji"',
                        '"Segoe UI Emoji"',
                        '"Segoe UI Symbol"',
                        '"Noto Color Emoji"'
                    ]
                },
                colors: {
                    neutral: {
                        50: "#f7f7f7",
                        100: "#eeeeee",
                        200: "#e0e0e0",
                        300: "#cacaca",
                        400: "#b1b1b1",
                        500: "#999999",
                        600: "#7f7f7f",
                        700: "#676767",
                        800: "#545454",
                        900: "#464646",
                        950: "#282828"
                    },
                    primary: {
                        50: "#f3f1ff",
                        100: "#e9e5ff",
                        200: "#d5cfff",
                        300: "#b7a9ff",
                        400: "#9478ff",
                        500: "#7341ff",
                        600: "#631bff",
                        700: "#611bf8",
                        800: "#4607d0",
                        900: "#3c08aa",
                        950: "#220174",
                        DEFAULT: "#611bf8"
                    }
                }
            },
            fontSize: {
                xs: ["12px", {lineHeight: "19.200000000000003px"}],
                sm: ["14px", {lineHeight: "21px"}],
                base: ["16px", {lineHeight: "25.6px"}],
                lg: ["18px", {lineHeight: "27px"}],
                xl: ["20px", {lineHeight: "28px"}],
                "2xl": ["24px", {lineHeight: "31.200000000000003px"}],
                "3xl": ["30px", {lineHeight: "36px"}],
                "4xl": ["36px", {lineHeight: "41.4px"}],
                "5xl": ["48px", {lineHeight: "52.800000000000004px"}],
                "6xl": ["60px", {lineHeight: "66px"}],
                "7xl": ["72px", {lineHeight: "75.60000000000001px"}],
                "8xl": ["96px", {lineHeight: "100.80000000000001px"}],
                "9xl": ["128px", {lineHeight: "134.4px"}]
            },
            borderRadius: {
                none: "0px",
                sm: "6px",
                DEFAULT: "12px",
                md: "18px",
                lg: "24px",
                xl: "36px",
                "2xl": "48px",
                "3xl": "72px",
                full: "9999px"
            },
            spacing: {
                0: "0px",
                1: "4px",
                2: "8px",
                3: "12px",
                4: "16px",
                5: "20px",
                6: "24px",
                7: "28px",
                8: "32px",
                9: "36px",
                10: "40px",
                11: "44px",
                12: "48px",
                14: "56px",
                16: "64px",
                20: "80px",
                24: "96px",
                28: "112px",
                32: "128px",
                36: "144px",
                40: "160px",
                44: "176px",
                48: "192px",
                52: "208px",
                56: "224px",
                60: "240px",
                64: "256px",
                72: "288px",
                80: "320px",
                96: "384px",
                px: "1px",
                0.5: "2px",
                1.5: "6px",
                2.5: "10px",
                3.5: "14px"
            }
        },
        plugins: [],
        important: "#webcrumbs"
    }
</script>
</body>
</html>
