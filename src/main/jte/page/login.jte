@import app.onlineschool.dto.RegisterLoginPage
@param RegisterLoginPage page

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            text-align: center;
        }

        .container {
            text-align: center;
            max-width: 400px;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .title {
            font-size: 36px;
            font-weight: bold;
            color: darkorange;
            margin-bottom: 20px;
            position: relative;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .title::before {
            position: absolute;
            content: "";
            width: 18px;
            height: 18px;
            border-radius: 50%;
            background-color: orange;
            left: -30px;
        }

        .title::after {
            position: absolute;
            content: "";
            width: 18px;
            height: 18px;
            border-radius: 50%;
            background-color: orange;
            left: -30px;
            animation: pulse 1s infinite;
        }

        @keyframes pulse {
            0% {
                transform: scale(0.9);
                opacity: 1;
            }
            100% {
                transform: scale(1.8);
                opacity: 0;
            }
        }

        .error-messages {
            margin-bottom: 20px;
            padding: 10px;
            background-color: #ffdddd;
            border: 1px solid #ff5c5c;
            border-radius: 5px;
            color: #d8000c;
            font-size: 14px;
            text-align: left;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            font-size: 16px;
            margin-bottom: 5px;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .button {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: darkorange;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
            width: 100%;
            box-sizing: border-box;
        }

        .button:hover {
            background-color: orange;
        }

        .button:active {
            background-color: darkorange;
        }

        .link {
            display: block;
            margin-top: 15px;
            font-size: 14px;
            text-decoration: none;
            color: darkorange;
        }

        .link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        @if (page != null && !page.getErrors().isEmpty())
            <div class="error-messages">
                <ul>
                    @for (String error : page.getErrors())
                        <li>${error}</li>
                    @endfor
                </ul>
            </div>
        @endif
        <div class="title">Sign In</div>
        <form method="post" action="/login">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" value="${page.getUsername()}" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="button">Sign In</button>
        </form>
        <a href="/register" class="link">Don't have an account? Sign up here</a>
    </div>
</body>
</html>
