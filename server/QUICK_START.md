# MultiPlatform API Server - Quick Start Guide

## What Was Created

I've successfully created a complete authentication API server with the following features:

### API Endpoints
1. **POST /api/register** - Register new users
2. **POST /api/login** - User login with JWT token
3. **POST /api/forgot-password** - Request password reset
4. **POST /api/reset-password** - Reset password with token
5. **GET /health** - Health check endpoint

### Key Features
- ✅ MongoDB database integration
- ✅ JWT (JSON Web Token) authentication
- ✅ BCrypt password hashing
- ✅ Email-based password reset
- ✅ Error handling and validation
- ✅ Security best practices

## Quick Start (5 Minutes)

### Step 1: Install MongoDB

**Option A: Using Homebrew (macOS)**
```bash
brew install mongodb-community
brew services start mongodb-community
```

**Option B: Using Docker**
```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

**Option C: Atlas Cloud**
- Visit https://www.mongodb.com/cloud/atlas
- Create a free cluster
- Copy your connection string

### Step 2: Setup Environment Variables

```bash
cd /Users/taqieallah/Downloads/MultiPlatform/server
cp .env .env
```

Edit `.env` file with your settings:
```
MONGODB_URI=mongodb://localhost:27017
DATABASE_NAME=multiplatform_db
JWT_SECRET=your-super-secret-key-change-this
EMAIL_SENDER=your-email@gmail.com
EMAIL_PASSWORD=your-app-password
```

### Step 3: Run the Server

```bash
cd /Users/taqieallah/Downloads/MultiPlatform
./gradlew server:run
```

The server will start on: **http://localhost:8080**

## Testing the API

### 1. Register a User
```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "name": "John Doe",
    "password": "SecurePass123!"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "token": "eyJ0eXAiOiJKV1QiLCJhbGc...",
  "user": {
    "id": "507f1f77bcf86cd799439011",
    "email": "john@example.com",
    "name": "John Doe",
    "createdAt": "2024-01-15T10:30:00.123456"
  }
}
```

### 2. Login User
```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "SecurePass123!"
  }'
```

### 3. Request Password Reset
```bash
curl -X POST http://localhost:8080/api/forgot-password \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com"
  }'
```

### 4. Reset Password (with token from email)
```bash
curl -X POST http://localhost:8080/api/reset-password \
  -H "Content-Type: application/json" \
  -d '{
    "token": "reset-token-from-email",
    "newPassword": "NewSecurePass123!"
  }'
```

## File Structure

```
server/
├── build.gradle.kts                    # Dependencies and build config
├── .env.example                        # Environment variables template
├── API_DOCUMENTATION.md                # Full API docs
├── README.md                           # Server README
└── src/main/kotlin/com/vf/multi/
    ├── Application.kt                  # Main application entry
    ├── config/
    │   └── AppConfig.kt               # Configuration from env vars
    ├── models/
    │   └── User.kt                    # User model & request/response types
    ├── database/
    │   └── UserRepository.kt          # MongoDB operations
    ├── utils/
    │   └── AuthUtils.kt               # Password hashing & JWT generation
    ├── services/
    │   └── EmailService.kt            # Email sending service
    └── routes/
        └── AuthRoutes.kt              # API endpoints
```

## Configuration Details

### JWT Token
- **Expiration:** 24 hours
- **Algorithm:** HS256 (HMAC SHA-256)
- **Secret:** From JWT_SECRET env var

### Password Reset
- **Token Type:** UUID v4
- **Expiration:** 1 hour
- **Delivery:** Email

### Email Setup (Gmail)

1. Enable 2-Factor Authentication on Gmail
2. Generate App Password:
   - Visit: https://myaccount.google.com/apppasswords
   - Create new app password
3. Use 16-character password in .env

## Database Schema

### Users Collection
```json
{
  "_id": "ObjectId",
  "email": "user@example.com",
  "name": "User Name",
  "passwordHash": "$2a$12$bcrypt_hash...",
  "createdAt": "2024-01-15T10:30:00.123456",
  "updatedAt": "2024-01-15T10:30:00.123456",
  "isActive": true,
  "resetToken": null,
  "resetTokenExpiry": null
}
```

## Troubleshooting

### MongoDB Connection Error
```
Error: connect ECONNREFUSED 127.0.0.1:27017
```
**Solution:** Start MongoDB service
```bash
brew services start mongodb-community  # macOS
```

If your MongoDB username/password contains special characters (like `@` or `:`), URL-encode them in `MONGODB_URI`:
- `@` -> `%40`
- `:` -> `%3A`

### Port 8080 Already in Use
```
Address already in use
```
**Solution:** Kill the process using port 8080
```bash
lsof -i :8080
kill -9 <PID>
```[QUICK_START.md](QUICK_START.md)

### Email Not Sending
**Check:**
1. EMAIL_SENDER is correct Gmail address
2. EMAIL_PASSWORD is App Password (16 chars)
3. 2-Factor Auth is enabled
4. SMTP port 587 is not blocked

### JWT Token Invalid
**Solution:** Generate new token
- Token expires after 24 hours
- Copy token from login/register response
- Use as: `Authorization: Bearer <token>`

## Production Checklist

- [ ] Change JWT_SECRET to a strong random string
- [ ] Use environment variables (not .env in prod)
- [ ] Enable HTTPS/SSL
- [ ] Use MongoDB Atlas or managed service
- [ ] Implement rate limiting
- [ ] Add CORS if needed
- [ ] Enable database backups
- [ ] Monitor logs and errors
- [ ] Set up CI/CD pipeline
- [ ] Add email verification on registration

## Next Steps

### To Extend the API
1. Add protected routes (verify JWT token)
2. Add user profile endpoints
3. Add refresh tokens
4. Add OAuth2 integration
5. Add rate limiting
6. Add API key authentication

### Example: Protected Route
```kotlin
post("/api/profile") {
    val token = call.request.header("Authorization")
        ?.removePrefix("Bearer ") ?: return@post call.respond(
            HttpStatusCode.Unauthorized,
            MessageResponse(false, "Missing token")
        )
    
    val userId = AuthUtils.verifyToken(token)
        ?: return@post call.respond(
            HttpStatusCode.Unauthorized,
            MessageResponse(false, "Invalid token")
        )
    
    val user = userRepository.findById(userId)
        ?: return@post call.respond(
            HttpStatusCode.NotFound,
            MessageResponse(false, "User not found")
        )
    
    call.respond(HttpStatusCode.OK, user)
}
```

## Additional Resources

- **Ktor Documentation:** https://ktor.io/docs
- **MongoDB Documentation:** https://docs.mongodb.com/
- **JWT.io:** https://jwt.io/
- **BCrypt:** https://www.mindrot.org/projects/bcrypt/

## Support

For issues or questions:
1. Check API_DOCUMENTATION.md for full API reference
2. Review error messages in server logs
3. Verify MongoDB connection
4. Check environment variables
5. Review API_DOCUMENTATION.md for examples

Happy coding! 🚀

