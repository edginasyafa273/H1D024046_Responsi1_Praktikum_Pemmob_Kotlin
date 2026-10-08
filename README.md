# ResepKu
> Platform eksplorasi panduan memasak interaktif berbasis Android modern dengan integrasi TheMealDB API.

---

## 👤 Identitas Praktikan
- **Nama Lengkap:** Edgina Syafa Ayu Wicaksono
- **NIM:** H1D024046
- **Shift Awal:** Shift D
- **Shift Akhir:** Shift E
- **Link Video Demo/Penjelasan:** [YouTube](https://...) | [Google Drive](https://...)

---

## 📱 Deskripsi Aplikasi
**ResepKu** hadir sebagai solusi mobile bagi siapa saja yang gemar memasak maupun yang baru ingin mulai belajar memasak. Sering kali orang kebingungan mencari takaran bumbu yang tepat dan panduan memasak yang runtut saat berada di dapur. 

Aplikasi ini mengatasi kendala tersebut dengan menyediakan katalog masakan global yang datanya ditarik secara *real-time* dari **TheMealDB REST API**. Dibangun dengan pendekatan modern **Jetpack Compose** dan arsitektur **MVVM**, aplikasi ini menyuguhkan antarmuka yang gesit, estetik, dan responsif terhadap perubahan data.

---

## 🛠️ Penjelasan Teknis

### 1. Spesifikasi & Tech Stack
- **Bahasa Pemrograman:** Kotlin 2.2.10
- **UI Toolkit:** Jetpack Compose (Material Design 3)
- **Minimum SDK:** 29 (Android 10.0) | **Target SDK:** 37 (Android 16)
- **Arsitektur:** Model-View-ViewModel (MVVM) didukung Repository Pattern
- **Pustaka (Dependencies):**
  - `Navigation Compose` — Manajemen alur perpindahan antarlayar
  - `ViewModel & StateFlow` — Pengelolaan state UI reaktif & tahan rotasi
  - `Retrofit 2 + Gson Converter` — Pengambilan & konversi data REST API
  - `OkHttp Logging Interceptor` — Pemantauan arus lalu lintas request HTTP
  - `Coil Compose` — Pemuatan gambar asinkron berkinerja tinggi
  - `Coroutines` — Penanganan proses latar belakang & fungsi penundaan pencarian

### 2. Fitur Utama
- **Eksplorasi Katalog Resep (`LazyVerticalGrid`):** Menyajikan aneka masakan dunia dalam format grid 2 kolom dengan pemuatan memori efisien via `RecipeCard` (dilengkapi visual makanan, badge kategori, dan penanda asal negara).
- **Pencarian Nama Masakan Cerdas:** Pengguna dapat mengetik masakan yang diinginkan dengan sistem *debouncing coroutine 500ms* untuk mencegah pemanggilan berlebih ke server, disertai tombol pembersih kolom teks instan.
- **Layar Rincian Resep & Panduan Langkah:** Menampilkan banner makanan berukuran besar, label asal masakan, konversi otomatis 20 bahan & takaran bumbu dari API, petunjuk memasak komprehensif, serta akses langsung ke video YouTube.
- **Penanganan Kondisi Antarmuka (*State-Driven UI*):** Menyediakan visual khusus untuk 4 kondisi data berbeda: saat memuat (*Loading Indicator*), data sukses tampil (*Success*), masakan tidak ditemukan (*Empty State*), dan kondisi koneksi gagal (*Error State* yang disertai tombol *Coba Lagi*).

### 3. Struktur Direktori Proyek
```text
app/src/main/java/com/pemmob/resepku/
├── data/
│   ├── model/         # Definisi model data (MealResponse, MealDto, Ingredient)
│   ├── remote/        # Kebutuhan API (MealApiService) dan RetrofitClient
│   └── repository/    # MealRepository (Penyedia sumber data utama)
├── ui/
│   ├── components/    # Komponen composable pakai ulang (RecipeCard, SearchBar, StateViews)
│   ├── detail/        # Layar DetailScreen & DetailViewModel
│   ├── home/          # Layar HomeScreen & HomeViewModel
│   ├── navigation/    # Pengaturan rute layar (AppNavGraph & Screen)
│   └── theme/         # Palet warna merah tua, tipografi, dan tema aplikasi
├── utils/             # Pembungkus status data (UiState sealed interface)
└── MainActivity.kt    # Titik awal eksekusi program Android
```

---

## 📸 Tangkapan Layar (Screenshots)

| Home Screen (Katalog) | Pencarian (Search) | Detail Screen (Bahan) | Detail Screen (Instruksi) |
|:---:|:---:|:---:|:---:|
| *(Tempel Foto 1)* | *(Tempel Foto 2)* | *(Tempel Foto 3)* | *(Tempel Foto 4)* |

---

## 🚀 Cara Menjalankan Proyek

1. **Prasyarat Sistem:**
   - Android Studio (versi terkini disarankan: Ladybug / Koala).
   - Java Development Kit (JDK) 17 ke atas.
   - Emulator Android atau HP Fisik (Aktifkan Mode Pengembang & USB Debugging).
   - Akses internet aktif untuk pengunduhan library Gradle dan penarikan data TheMealDB.

2. **Langkah Pengerjaan:**
   ```bash
   # Clone repository ke komputer lokal
   git clone https://github.com/edginasyafa273/H1D024046_Responsi1_Praktikum_Pemmob_Kotlin.git
   ```
   - Buka direktori proyek melalui menu **Open** di Android Studio.
   - Tunggu proses **Gradle Sync** sampai selesai sepenuhnya.
   - Tentukan emulator atau perangkat fisik sebagai tujuan pemasangan.
   - Jalankan program dengan menekan tombol **Run (`Shift + F10`)**.
