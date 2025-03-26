@import app.onlineschool.dto.RegisterLoginPage
@param RegisterLoginPage page

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
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
