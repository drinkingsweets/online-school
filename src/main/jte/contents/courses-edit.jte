@import app.onlineschool.dto.CourseEditPage
@param CourseEditPage page

@template.layout.sidebar(content =
@`
    <body class="bg-gray-100 flex justify-center items-center min-h-screen p-4">
    <div class="bg-white shadow-lg w-full h-screen flex flex-col p-6 relative">
        <form action="/courses/${page.getLesson().getCourse().getId()}/${page.getLesson().getLessonNumber()}/delete"
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

        <form action="/courses/${page.getLesson().getCourse().getId()}/${page.getLesson().getLessonNumber()}/edit" method="post" class="flex flex-col space-y-4 flex-grow mt-8">
            <input type="text" id="title" name="title" value="${page.getLesson().getTitle()}" required
                   class="w-full text-3xl font-semibold outline-none border-transparent bg-transparent focus:bg-gray-100 p-2">
            <textarea name="content" required
                      class="w-full h-full text-lg outline-none border-transparent bg-transparent focus:bg-gray-100 focus:ring-0 transition p-2 resize-none">${page.getLesson().getContent()}</textarea>

            <div class="flex justify-between">
                <div class="flex flex-col space-y-4">
                    @if (page.getPreviousLesson() != 0)
                        <a href="/courses/${page.getLesson().getCourse().getId()}/${page.getPreviousLesson()}/edit"
                           class="bg-gray-600 text-white px-6 py-2 rounded-lg hover:bg-gray-700 transition flex items-center justify-center w-24">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                                <path fill-rule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd"/>
                            </svg>
                        </a>
                    @endif
                    <a href="/test/${page.getLesson().getCourse().getId()}/${page.getLesson().getLessonNumber()}/create"
                       class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition flex items-center justify-center">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z" clip-rule="evenodd"/>
                        </svg>
                    </a>
                </div>
                <div class="flex flex-col space-y-4">
                    <a href="/courses/${page.getLesson().getCourse().getId()}/${page.getNextLesson()}/edit"
                       class="bg-orange-600 text-white px-6 py-2 rounded-lg hover:bg-orange-700 transition flex items-center justify-center w-24">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"/>
                        </svg>
                    </a>
                    <button type="submit"
                            class="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition flex items-center justify-center w-24"
                            title="Save">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                            <path d="M7.707 10.293a1 1 0 10-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L11 11.586V6h5a2 2 0 012 2v7a2 2 0 01-2 2H4a2 2 0 01-2-2V8a2 2 0 012-2h5v5.586l-1.293-1.293zM9 4a1 1 0 012 0v2H9V4z"/>
                        </svg>
                    </button>
                </div>
            </div>
        </form>
    </div>
    </body>
`)