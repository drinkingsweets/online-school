@import app.onlineschool.model.User
@param User author

@template.layout.sidebar(content = @`
    <body>
    <div class="author-page">
        <div class="author-header">
            <img src="${author.getPfpLink()}" alt="${author.getFullName()}" class="author-avatar">
            <div class="author-info">
                <h1 class="author-name">${author.getFullName()}</h1>
                <p class="author-title">Основатель онлайн-школы</p>
                <div class="social-links">
                    <a href="https://github.com/drinkingsweets" class="social-link">GitHub</a>
                    <a href="https://t.me/rain_aga1n" class="social-link">Telegram</a>
                </div>
            </div>
        </div>

        <div class="author-bio">
            <p>Привет! Я ${author.getFullName()}, создатель этой образовательной платформы.</p>
            <p>🎓 Являюсь студентом второго курса Финансового университета, направление Технологии Разработки Программного Обеспечения.</p>
            <p>Данный проект является курсовой работой по дисциплине Современные технологии программирования.</p>
            <p>📅 Начало проекта: 28.02.2025<br>
                📌 Завершение: 31.04.2025</p>
        </div>

        <div class="author-tech">
            <h2 class="tech-title">Используемые технологии:</h2>
            <div class="tech-grid">
                <div class="tech-tile">Spring Boot</div>
                <div class="tech-tile">JTE</div>
                <div class="tech-tile">PostgreSQL</div>
                <div class="tech-tile">HTML</div>
                <div class="tech-tile">CSS</div>
            </div>
        </div>
        <br/>
        <br/>

        <div class="author-experience">
            <h2 class="tech-title">Опыт работы с технологиями:</h2>
            <ul class="experience-list">
                <li><strong>Spring Boot</strong> — около 6 месяцев</li>
                <li><strong>JTE</strong> — 1 год</li>
                <li><strong>PostgreSQL</strong> — 1.5 года</li>
                <li><strong>HTML/CSS</strong> — около 6 месяцев</li>
            </ul>
        </div>
    </div>
    </body>
`)
