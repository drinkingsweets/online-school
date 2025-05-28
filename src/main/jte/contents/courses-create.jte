@template.layout.sidebar(content =
@`
<body class="bg-gray-100 flex justify-center items-center min-h-screen p-4">
    <div class="bg-white shadow-lg w-full h-screen flex flex-col p-6">
        <form action="/courses/create" method="post" class="flex flex-col space-y-4 flex-grow">
        <input type="text" id="title" name="title" placeholder="Untitled" required
               class="w-full text-3xl font-semibold outline-none border-transparent bg-transparent focus:bg-gray-100 p-2">
            <textarea name="description" placeholder="Course Description" required
                      class="w-full h-full text-lg outline-none border-transparent bg-transparent focus:bg-gray-100 focus:ring-0 transition p-2 resize-none"></textarea>
            <button type="submit" class="bg-orange-500 text-white px-4 py-2 rounded-lg hover:bg-orange-600 transition self-end">
                Save
            </button>
        </form>
    </div>
</body>
`)





