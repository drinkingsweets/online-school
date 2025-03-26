@import app.onlineschool.model.User
@import app.onlineschool.dto.ProfilePage
@param ProfilePage page

@template.layout.sidebar(content =
@`
    <body class="bg-gray-100 flex justify-center items-center min-h-screen p-4">
    <div class="bg-white shadow-lg w-full h-screen flex flex-col md:flex-row items-center md:items-start p-6">

        <!-- Левая часть (Фото + ID) -->
        <div class="flex flex-col items-center w-1/3">
            <span class="text-gray-500 text-sm mb-2">ID: ${page.getUser().getId()}</span>
            <img src="${page.getUser().getPfpLink()}" alt="Аватар" class="w-32 h-32 rounded-full border">
        </div>

        <!-- Правая часть (Инфо о пользователе) -->
        <div class="w-2/3 pl-6">
            <h2 class="text-xl font-semibold">${page.getUser().getFullName()}</h2>
            <p class="text-gray-700 mt-2"><strong>Username:</strong> ${page.getUser().getUsername()}</p>
            <p class="text-gray-700 mt-1"><strong>Date of
                    registration:</strong> ${page.getUser().getCreatedAt().toString()}</p>
            <p class="text-gray-700 mt-1"><strong>Amount of added
                    courses:</strong> ${page.getUser().getCourses().size()}</p>

            @if(!page.getSuccess().isEmpty())
                <div class="bg-green-100 text-green-700 p-4 rounded mb-4">
                    ${page.getSuccess()}
                </div>
            @endif
            @if(!page.getError().isEmpty())
                <div class="bg-red-100 text-red-700 p-4 rounded mb-4">
                    ${page.getError()}
                </div>
            @endif

            <form action="/profile" method="post" class="flex flex-col space-y-2 mt-8">
                <input id="oldPassword" name="oldPassword" placeholder="Old password..." required
                       class="p-2 border rounded">
                <input id="newPassword" name="newPassword" placeholder="New password..." required
                       class="p-2 border rounded">
                <input id="newPasswordRepeat" name="newPasswordRepeat" placeholder="Repeat new password..." required
                       class="p-2 border rounded">
                <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition">
                    Change password
                </button>
            </form>

            <form action="/profile/picture_change" method="post" class="flex flex-col space-y-2 mt-4">
                <input type="link" name="link" id="link" placeholder="Put link to new profile picture..."
                       class="p-2 border rounded" required>
                <button type="submit"
                        class="bg-orange-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition">
                    Change profile picture
                </button>
            </form>

            <button id="deleteUserButton"
                    class="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition mt-8">
                Delete User
            </button>

            <form id="deleteUserForm" action="/profile/delete" method="post"
                  class="flex flex-col space-y-2 mt-4 hidden">
                <input type="password" name="password" id="deletePassword" placeholder="Confirm password..." required
                       class="p-2 border rounded">
                <button type="submit" class="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition">
                    Confirm Delete
                </button>
            </form>
        </div>
    </div>
    <script>
        document.getElementById('deleteUserButton').addEventListener('click', function () {
            document.getElementById('deleteUserForm').classList.toggle('hidden');
        });
    </script>
    </body>`)