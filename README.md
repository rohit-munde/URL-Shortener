# URL Shortener UI 🚀

A sleek, single-page React application for shortening long URLs. Built with **Vite**, **React**, and **Material UI**.

## 📸 Screenshot

![URL Shortener UI](./screenshot.png)

## ✨ Features

- **Clean & Modern Interface**: Built with Material UI components for a premium look.
- **Instant Shortening**: Fast API integration to generate short URLs on the fly.
- **Smart Proxying**: Bypasses browser CORS and `localhost` HTTP restrictions by securely proxying requests directly through the Vite dev server.
- **Seamless Redirection**: Clicking the generated short URL securely forwards the GET request to the backend and handles the `302` redirect.

## 🛠️ Tech Stack

- **Frontend Framework**: React + TypeScript
- **Build Tool**: Vite
- **Styling & UI**: SCSS + Material UI (`@mui/material`)

## 🚀 Getting Started

### Prerequisites
Make sure you have [Node.js](https://nodejs.org/) installed on your machine.
You will also need your Java Spring Boot backend running on port `8080`.

### Installation

1. Install dependencies:
   ```bash
   npm install
   ```
2. Start the development server:
   ```bash
   npm run dev
   ```
3. Open your browser and navigate to the URL provided by Vite (usually `http://localhost:5173`).

### Backend Integration
The app relies on your Java backend running at `localhost:8080`.
- **POST `/shorten`**: Receives the original URL and generates the short URL ID.
- **GET `/{id}`**: Redirects the user to the original URL.
*(All requests are automatically proxied via Vite's `vite.config.ts` to avoid CORS issues).*

## 📝 License
This project is open-source and available under the MIT License.
