# Documentación de la API del Proyecto Zenit

Esta sección detalla todos los endpoints disponibles en la API, organizados por controladores. La mayoría de las rutas requieren autenticación mediante un token JWT en el encabezado `Authorization`.

## 📌 Resumen de Endpoints

---

### 🏃 ActivityRecordController
Gestiona el registro y el historial de actividades físicas de los usuarios.

| Método | Ruta | Descripción | Request Body | Respuesta (Éxito) |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/activity/save` | Registra una nueva actividad física para el usuario en sesión. | `ActivityRecordRequestDTO` | `ActivityRecordResponseDTO` |
| **GET** | `/activity/history` | Recupera la lista completa de actividades realizadas por el usuario. | N/A | `List<ActivityRecordResponseDTO>` |
| **DELETE** | `/activity/delete/{id}` | Elimina un registro de actividad específico mediante su ID. | N/A | `204 No Content` |

---

### 🔐 AuthController
Controlador encargado de la gestión de acceso, registro de usuarios y verificación de cuenta.
*Ruta base: `/auth`*

| Método | Ruta | Descripción | Request Body | Respuesta (Éxito) |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/auth/login` | Autentica al usuario y devuelve un token JWT. | `Map<String, String>` (email, password) | `Map<String, String>` (token) |
| **POST** | `/auth/register` | Crea una nueva cuenta de usuario en el sistema. | `UserDTO` | `"User registered succesfully!"` |
| **POST** | `/auth/send/verification` | Envía un correo electrónico con el enlace de verificación de cuenta. | `Map<String, String>` (email) | `boolean` (éxito de envío) |
| **GET** | `/auth/verify` | Procesa la verificación de la cuenta (usado desde el email). | Param: `userId` (Long) | `"Cuenta verificada correctamente"` |
| **GET** | `/auth/is/verified` | Consulta si un email específico ya está verificado. | Param: `email` (String) | `Map<String, Boolean>` |
| **POST** | `/auth/data/compilation` | Guarda la información adicional del perfil del usuario (edad, peso, etc). | `UserProfileDTO` | `200 OK` |

---

### 🧠 DailyEmotionalStateController
Permite realizar el seguimiento del bienestar emocional diario.

| Método | Ruta | Descripción | Request Body | Respuesta (Éxito) |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/emotional/save` | Guarda el estado de ánimo o emocional del día actual. | `DailyEmotionalStateRequestDTO` | `DailyEmotionalStateResponseDTO` |
| **GET** | `/emotional/show` | Obtiene los registros emocionales del mes actual. | N/A | `List<DailyEmotionalStateResponseDTO>` |

---

### 👣 DailyStepsController
Gestión y monitorización del conteo de pasos diarios y estadísticas semanales.

| Método | Ruta | Descripción | Request Body | Respuesta (Éxito) |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/steps/save` | Registra o actualiza el número de pasos para el día actual. | `DailyStepsRequestDTO` | `DailyStepsResponseDTO` |
| **GET** | `/steps/today` | Consulta el total de pasos registrados hoy. | N/A | `DailyStepsResponseDTO` |
| **GET** | `/steps/week` | Obtiene el desglose de estadísticas de la semana actual. | N/A | `WeekStatsResponseDTO` |
| **GET** | `/steps/history` | Devuelve el historial completo de pasos del usuario. | N/A | `List<DailyStepsResponseDTO>` |

---

### ⭐ HabitController
Permite a los usuarios crear hábitos y marcar su cumplimiento diario.

| Método | Ruta | Descripción | Request Body | Respuesta (Éxito) |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/habit/save` | Crea un nuevo hábito personalizado. | `HabitRequestDTO` | `HabitResponseDTO` |
| **GET** | `/habit/show` | Lista todos los hábitos del usuario. | N/A | `List<HabitResponseDTO>` |
| **DELETE** | `/habit/delete/{id}` | Elimina un hábito por completo. | N/A | `204 No Content` |
| **POST** | `/habit/check/{id}` | Marca un hábito como completado para el día de hoy. | N/A | `HabitResponseDTO` |

---

### 📓 JournalController
Sistema de diario personal para reflexiones y notas del usuario.

| Método | Ruta | Descripción | Request Body | Respuesta (Éxito) |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/journal/create` | Crea una nueva entrada en el diario personal. | `JournalRequestDTO` | `JournalResponseDTO` |
| **GET** | `/journal/entries` | Obtiene todas las entradas del diario del usuario. | N/A | `List<JournalResponseDTO>` |
| **DELETE** | `/journal/delete/{id}` | Elimina una entrada del diario específica. | N/A | `204 No Content` |

---

### 🍳 RecipeController
Módulo para la obtención de recetas saludables personalizadas.

| Método | Ruta | Descripción | Request Body | Respuesta (Éxito) |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/recipe/request` | Solicita la generación de una receta | `RecipeRequestDTO` | `String` (Contenido de la receta) |

---

### 👤 UserController
Gestión de datos de perfil del usuario autenticado.
*Ruta base: `/user`*

| Método | Ruta | Descripción | Request Body | Respuesta (Éxito) |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/user/account/profile` | Obtiene todos los detalles del perfil del usuario actual. | N/A | `UserProfileDTO` |
| **GET** | `/user/name` | Devuelve el nombre del usuario autenticado. | N/A | `String` (nombre) |

---

### 🏋️ WorkoutExerciseController
Gestión de rutinas de ejercicio organizadas por días de la semana.

| Método | Ruta | Descripción | Request Body | Respuesta (Éxito) |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/workout/save` | Añade un ejercicio a la rutina del usuario. | `WorkoutExerciseRequestDTO` | `WorkoutExerciseResponseDTO` |
| **GET** | `/workout/{weekDay}` | Lista los ejercicios programados para un día concreto (ej: Monday). | N/A | `List<WorkoutExerciseResponseDTO>` |
| **DELETE** | `/workout/delete/{id}` | Elimina un ejercicio específico de la rutina. | N/A | `204 No Content` |