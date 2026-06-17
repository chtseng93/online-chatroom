# Online Chatroom

A real-time online chatroom built with **Spring Boot** and **WebSocket (STOMP)**. The frontend is powered by **Thymeleaf**, **JavaScript**, and **jQuery**.

## Features

- **Group Chat** — Send messages to all online users in a shared public channel
- **Private Chat** — Click on any user in the sidebar to start a one-on-one private conversation
- **Live User List** — The online member list updates automatically as users join or leave
- **Gender Avatars** — Users select male or female at login and are assigned a matching avatar

## Demo

### Group Chat & Private Chat

After logging in with a nickname and selecting your gender, you will enter the chatroom where you can:
- Switch to **Group Chat** (All) to broadcast messages to everyone
- Click a **user's name** in the sidebar to switch to a one-on-one private conversation

![demo](demo.gif)

### Exit

Click the **Exit** button to leave the chatroom. Other users will see a notification that you have left, and you will be redirected back to the login page.

![demo2](demo2.gif)

## Screenshots

![image](https://github.com/user-attachments/assets/a25f3891-89c7-4bb9-a39a-96817d023aa2)
![image](https://github.com/user-attachments/assets/1f0aab73-873f-42ad-b5a5-4e1bfea3d97a)
![image](https://github.com/user-attachments/assets/ed4b7e9c-3225-4fc5-b7af-0285ac729ae0)
![image](https://github.com/user-attachments/assets/c5296c90-d2cb-4c76-b21f-46a8f3e216a2)

## Tech Stack

- **Backend**: Java 8, Spring Boot 2.6.5, Spring WebSocket, STOMP
- **Frontend**: Thymeleaf, JavaScript, jQuery, SockJS
- **Containerization**: Docker

## Run Locally

```bash
docker compose up --build
```

Then open `http://localhost:8086/chat/entry` in your browser.
Open a second browser window (or incognito) to simulate a second user.

---

> This project is for non-commercial use. Background image source: https://www.goodfon.com/abstraction/wallpaper-linii-abstrakciya-fon-tochki.html. If there is any copyright infringement, please let me know and I will take it down.
