@param boolean isAdmin

@template.layout.sidebar(

content = @`<h1 class="text-2xl font-bold">Welcome back!</h1>
    <div class="mt-8 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 px-6">
        <a href="/courses" class="bg-white p-6 rounded-lg shadow hover:bg-gray-50 transition duration-200 text-center">
            <h3 class="text-xl font-semibold text-gray-800">Explore courses</h3>
            <p class="text-gray-600">Find new courses</p>
        </a>
        <a href="/mycourses"
           class="bg-white p-4 rounded-lg shadow hover:bg-gray-50 transition duration-200 text-center">
            <h3 class="text-xl font-semibold text-gray-800">Continue learning</h3>
            <p class="text-gray-600">View your courses, continue studying</p>
        </a>
        @if(isAdmin)
        <a href="/courses/create"
           class="bg-white p-4 rounded-lg shadow hover:bg-gray-50 transition duration-200 text-center">
            <h3 class="text-xl font-semibold text-gray-800">Add new course</h3>
            <p class="text-gray-600">Create new course(admin only)</p>
        </a>
        @endif
<%--        <a href="/progress" class="bg-white p-6 rounded-lg shadow hover:bg-gray-50 transition duration-200 text-center">--%>
<%--            <h3 class="text-xl font-semibold text-gray-800">Digital Marketing</h3>--%>
<%--            <p class="text-gray-600">Boost your brand with SEO and social media.</p>--%>
    </div>
<%--        </a>--%>
`
)