@import java.util.HashMap
@import java.util.List
@import java.util.Map
@import app.onlineschool.model.Course
@import app.onlineschool.model.User
@param List<Course> courses
@param Map<Course, User.CourseProgress> courseProgress

@template.layout.sidebar(
    content = @`
    <main class="p-10">
        <h2 class="text-2xl font-semibold text-gray-800 mb-6">My Courses</h2>
        <div class="grid grid-cols-1 gap-6">
            @if(courses.isEmpty() || courseProgress.isEmpty())
                <h3 class="text-2xl text-gray-600 mb-4">You haven't added any courses</h3>
            @else
                @for(Course course: courses)
                    <div class="bg-white p-10 rounded-lg shadow hover:bg-gray-50 transition duration-200 w-full">
                        <a href="/mycourses/${course.getId()}" class="block">
                            <h3 class="text-xl font-semibold text-gray-800">${course.getTitle()}</h3>
                            <p class="text-gray-600">${course.getShortDescription()}</p>
                        </a>
                        @if(courseProgress.get(course.getId()).isFinished())
    <%--                        <span class="text-sm text-green-600">Completed</span>--%>
                            <div class="mt-4">
                            <div class="h-2 bg-gray-200 rounded-full">
                                <div class="h-2 bg-green-600 rounded-full" style="width: ${course.getLessons().isEmpty() ? 0 : (100)}%;"></div>
                            </div>
                                <p class="text-sm text-gray-600 mt-1">Completed!</p>
                            </div>
                        @else
                        <div class="mt-4">
                            <div class="h-2 bg-gray-200 rounded-full">
                                <div class="h-2 bg-orange-600 rounded-full" style="width: ${course.getLessons().isEmpty() ? 0 : (((courseProgress.get(course.getId()).getCompletedLessons() - 1) * 100 / course.getLessons().size()))}%;"></div>
                            </div>
                            <p class="text-sm text-gray-600 mt-1">${course.getLessons().isEmpty() ? 0 : (((courseProgress.get(course.getId()).getCompletedLessons() - 1) * 100 / course.getLessons().size()))}% completed</p>
                        </div>
                            @endif
                    </div>
                @endfor
                @endif
        </div>
    </main>
`)