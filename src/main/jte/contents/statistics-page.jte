@import app.onlineschool.dto.StatisticsPage
@param StatisticsPage page

@template.layout.sidebar(content =
@`
    <body class="bg-gray-100 text-gray-900">
    <header class="justify-center">
        <h1 class="text-2xl font-bold">Statistics</h1>
    </header>

    <!-- Totals Section -->
    <section class="container mx-auto p-4">
        <div class="bg-white p-4 rounded-lg shadow-md text-center">
            <p class="text-lg font-medium">Users Total: ${page.getUsersTotal()}</p>
            <p class="text-lg font-medium">Courses Total: ${page.getCoursesTotal()}</p>
            <p class="text-lg font-medium">Lessons Total: ${page.getLessonsTotal()}</p>
        </div>
    </section>

    <section class="container mx-auto p-4">
        <div class="flex flex-wrap gap-4 justify-center">
            <div class="bg-white p-4 rounded-lg shadow-md flex-1 min-w-[400px]">
                <h2 class="text-xl font-bold mb-2 text-center">User Activity</h2>
                <div class="relative" style="height:200px">
                    <canvas id="userActivityChart"></canvas>
                </div>
            </div>

            <div class="bg-white p-4 rounded-lg shadow-md flex-1 min-w-[400px]">
                <h2 class="text-xl font-bold mb-2 text-center">Course Enrollment</h2>
                <div class="relative" style="height:200px">
                    <canvas id="courseEnrollmentChart"></canvas>
                </div>
            </div>
        </div>
    </section>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // User Activity Chart Data
        const userActivityLabels = [
            @for(String label :  page.getUserActivityLabels())
                "${label}"@if(page.getUserActivityLabels().indexOf(label) < page.getUserActivityLabels().size() - 1),@endif
            @endfor
        ];
        const userActivityData = [
            @for(int data :page.getUserActivityData())
                ${data}@if(page.getUserActivityData().indexOf(data) < page.getUserActivityData().size() - 1),@endif
            @endfor
        ];

        // Course Enrollment Chart Data
        const courseEnrollmentLabels = [
            @for(String label : page.getCourseEnrollmentLabels())
                "${label}"@if(page.getCourseEnrollmentLabels().indexOf(label) < page.getCourseEnrollmentLabels().size() - 1),@endif
            @endfor
        ];
        const courseEnrollmentData = [
            @for(int data : page.getCourseEnrollmentData())
                ${data}@if(page.getCourseEnrollmentData().indexOf(data) < page.getCourseEnrollmentData().size() - 1),@endif
            @endfor
        ];

        // Wait for DOM to be fully loaded
        document.addEventListener('DOMContentLoaded', function() {
            // User Activity Chart
            const userActivityCtx = document.getElementById('userActivityChart').getContext('2d');
            new Chart(userActivityCtx, {
                type: 'line',
                data: {
                    labels: userActivityLabels,
                    datasets: [{
                        label: 'User Activity',
                        data: userActivityData,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            // Course Enrollment Chart
            const courseEnrollmentCtx = document.getElementById('courseEnrollmentChart').getContext('2d');
            new Chart(courseEnrollmentCtx, {
                type: 'bar',
                data: {
                    labels: courseEnrollmentLabels,
                    datasets: [{
                        label: 'Enrollments',
                        data: courseEnrollmentData,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        });
    </script>
    </body>
`)