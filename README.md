# OOP-Java-Group-Project
Командний проєкт студентів групи **ІН-33-4** з предмету **Об'єктно-орієнтоване програмування на мові Java**. 
GUI-додаток для роботи з таблицею в СУБД (JavaFX + JDBC).

# 🏢 Hostel Management System (JavaFX + JDBC)
## 📋 Опис проєкту
Цей застосунок призначений для автоматизації обліку поселення студентів у гуртожитки. Система дозволяє керувати даними про студентів, факультети, кімнати та контракти на проживання, забезпечуючи зручний інтерфейс для адміністратора. 

**Мета проєкту:** реалізація GUI-додатка з повним циклом CRUD-операцій та пошуку, використовуючи архітектурний шаблон MVC та пряме підключення до БД через JDBC.

--- 

## 💻 Використані технології

- **Java 21+**
- **JavaFX** — побудова графічного інтерфейсу
- **JDBC** — взаємодія з базою даних
- **PostgreSQL 18+** — СУБД для зберігання даних
- **Maven** — автоматизація збирання проєкту
- **MVC** — архітектура коду

--- 
## 🏗 Архітектура застосунку

Проєкт структурований згідно з принципами MVC:
- **Model:** Класи-сутності (Student, Room, Contract, Faculty) та DAO-шар для логіки роботи з БД
- **View:** FXML-файли та стилі для відображення інтерфейсу
- **Controller:** Обробники подій, що пов'язують UI з логікою додатка
- **DB Helper:** Клас для керування з'єднанням (використання db.properties)

--- 
## 🗄 Схема бази даних (Hostel)

Система базується на чотирьох основних таблицях:
- **Faculty** — довідник факультетів (ID, назва)
- **Student** — дані студентів (ID, ПІБ, номер телефону, Faculty_ID)
- **Room** — інформація про кімнати (ID, номер, місткість, вартість)
- **Contract** — реєстрація поселення (ID, Student_ID, Room_ID, дата початку, дата закінчення)

> Ключовий зв'язок: Таблиця `Contract` є центральною, пов'язуючи студента та кімнату з визначенням терміну проживання

--- 
## 👥 Склад команди та ролі
| Роль | Учасник | Обов'язки |
|------|------|------| 
| **Дмитренко Богдан** | *Team Lead* | Координація, архітектура БД, Code Review, опис предметної області |
| **Рубан Богдан** | *Developer 1* | Реалізація JDBC-логіки, створення DAO, робота з SQL-запитами |
| **Срібна Ольга** | *Developer 2* | Розробка GUI (JavaFX), зв'язування контролерів з моделлю, валідація |

--- 

## 🧾 Регламент роботи з Git

### Структура гілок

- `main` — релізна гілка (тільки стабільний код)
- `develop` — основна гілка інтеграції функціоналу
- `feature/name-of-feature` — для розробки нових функцій

--- 

#### Правила комітів

- `feat` — новий функціонал
- `fix` — виправлення помилки
- `refactor` — рефакторинг
- `docs` — документація
- `style` — форматування
- `test` — тести

--- 
### 📝 Приклади за типами комітів

#### 🚀 feat (New feature)
- `feat: add DBConnection class for PostgreSQL connection`
- `feat: implement method to add new student in StudentDAO`
- `feat: develop FXML interface for contract registration form`
- `feat: add room search function by number and capacity`

#### 🐛 fix (Bug fix)
- `fix: fix date display error in contracts table`
- `fix: fix syntax error in deleteStudent SQL query`
- `fix: add NullPointerException handling for empty value search`
- `fix: fix SQL numeric types display in JavaFX TableView`

#### 📚 docs (Documentation)
- `docs: update database schema in README.md`
- `docs: add JavaDoc for service layer methods`
- `docs: prepare conference abstracts (data types section)`
- `docs: add instructions for db.properties configuration`

#### 🛠 refactor (Refactoring)
- `refactor: move SQL queries from controller to separate DAO class`
- `refactor: optimize SQL Date to LocalDate conversion method`
- `refactor: rename variables in Student model for better readability`

#### 🎨 style (Code style)
- `style: format code according to Google Java Style Guide`
- `style: update CSS styles for the main application table`

#### 🧪 test (Testing)
- `test: add JUnit tests for faculty field validation`
- `test: verify SQL to Java type mapping correctness`

--- 

## 📂 Структура репозиторію
```
├── src/main/java
│   ├── com.hostel.app
│   │   ├── model/       # Класи даних та DAO
│   │   ├── view/        # JavaFX компоненти
│   │   ├── controller/  # Логіка обробки подій
│   │   └── util/        # JDBC Helper, Config loader
├── src/main/resources
│   ├── fxml/            # Розмітка інтерфейсу
│   ├── db.properties    # Конфігурація БД
├── docs/                # Згенерований JavaDoc
├── pom.xml              # Залежності Maven
└── README.md
```

---

## 📆 План робіт
- `01.03 - 08.03.2026` [x] Вибір теми, формування команди, розподіл ролей
- `09.03 - 22.03.2026` [x] Створення репозиторію, написання та подання тез
- `23.03 - 29.03.2026` [ ] Проєктування схеми бази даних та генерація SQL-скриптів
- `30.03 - 04.04.2026` [ ] Налаштування Maven, реалізація підключення до БД (JDBC)
- `05.04 - 15.04.2026` [ ] Реалізація шару DAO та базових CRUD-операцій
- `16.04 - 21.04.2026` [ ] Реалізація системи багатокритеріального пошуку
- `22.04 - 04.05.2026` [ ] Створення графічного інтерфейсу (JavaFX) та зв'язка з логікою
- `05.05 - 11.05.2026` [ ] Комплексне тестування, відловлювання багів, оптимізація
- `12.05 - 18.05.2026` [ ] Написання JavaDoc, підготовка презентації та фінальна здача

---