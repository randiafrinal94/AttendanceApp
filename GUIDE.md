# Panduan Menjalankan Aplikasi Attendance App

Berikut adalah langkah-langkah untuk menjalankan aplikasi Attendance App, yang terdiri dari Backend (Spring Boot) dan Frontend (Vue 3 + Vite).

## Prasyarat

Pastikan Anda telah menginstal perangkat lunak berikut di komputer Anda:

-   **Java JDK 17** atau yang lebih baru.
-   **Maven** (untuk membangun dan menjalankan Backend).
-   **Node.js** (versi LTS disarankan) dan **npm**.

## 1. Menjalankan Backend

Backend dibangun menggunakan Java Spring Boot.

1.  Buka terminal atau command prompt.
2.  Arahkan ke direktori `Backend`:
    ```bash
    cd Backend
    ```
3.  Jalankan aplikasi menggunakan Maven:
    ```bash
    mvn spring-boot:run
    ```

    *Alternatif jika Anda memiliki masalah dengan dependensi, Anda dapat membersihkan dan menginstall ulang terlebih dahulu:*
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

Tunggu hingga aplikasi berjalan. Backend biasanya akan berjalan di `http://localhost:8080`.

## 2. Menjalankan Frontend

Frontend dibangun menggunakan Vue 3 dan Vite.

1.  Buka terminal baru (biarkan terminal Backend tetap berjalan).
2.  Arahkan ke direktori `Frontend`:
    ```bash
    cd Frontend
    ```
3.  Instal dependensi (hanya perlu dilakukan pertama kali):
    ```bash
    npm install
    ```
4.  Jalankan server pengembangan:
    ```bash
    npm run dev
    ```

Setelah berhasil, terminal akan menampilkan URL lokal untuk mengakses aplikasi, biasanya di `http://localhost:5173`.

## 3. Mengakses Aplikasi

Buka browser web Anda dan kunjungi URL yang ditampilkan di terminal Frontend (misalnya `http://localhost:5173`). Anda sekarang dapat menggunakan aplikasi Attendance App.
