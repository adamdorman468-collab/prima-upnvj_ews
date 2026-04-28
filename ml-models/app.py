import streamlit as st

from predictor import predict_student_status

# -----------------------------------------
# 1. KONFIGURASI TAMPILAN HALAMAN WEB
# -----------------------------------------
st.set_page_config(page_title="EWS Kelulusan UPNVJ", page_icon="🎓", layout="centered")

# -----------------------------------------
# 2. MEMUAT "OTAK" AI (MODEL MACHINE LEARNING)
# -----------------------------------------
@st.cache_resource  # Agar model tidak di-load berulang kali yang bikin web lemot
def predict_cached(*, ips1: float, ips2: float, ips3: float, ips4: float, sks: int, jk: int):
    return predict_student_status(ips1=ips1, ips2=ips2, ips3=ips3, ips4=ips4, sks=sks, jk=jk)

# -----------------------------------------
# 3. DESAIN HEADER DASBOR
# -----------------------------------------
st.image("https://upload.wikimedia.org/wikipedia/id/thumb/7/7b/Logo_UPNVJ_-_Universitas_Pembangunan_Nasional_Veteran_Jakarta.png/300px-Logo_UPNVJ_-_Universitas_Pembangunan_Nasional_Veteran_Jakarta.png", width=100)
st.title("🎓 Dasbor Early Warning System (EWS)")
st.subheader("Prediksi Potensi Keterlambatan Lulus Mahasiswa Fasilkom UPNVJ")
st.markdown("---")
st.write("Masukkan data akademik mahasiswa hingga Semester 4 untuk mendeteksi dini potensi Drop Out (DO) atau keterlambatan masa studi.")

# -----------------------------------------
# 4. MEMBUAT FORM INPUT DATA MAHASISWA
# -----------------------------------------
col1, col2 = st.columns(2)

with col1:
    st.markdown("**Nilai IPS Tiap Semester**")
    ips1 = st.number_input("IPS Semester 1", min_value=0.0, max_value=4.0, value=3.0, step=0.1)
    ips2 = st.number_input("IPS Semester 2", min_value=0.0, max_value=4.0, value=3.0, step=0.1)
    ips3 = st.number_input("IPS Semester 3", min_value=0.0, max_value=4.0, value=3.0, step=0.1)
    ips4 = st.number_input("IPS Semester 4", min_value=0.0, max_value=4.0, value=3.0, step=0.1)

with col2:
    st.markdown("**Informasi Akademik Lainnya**")
    sks = st.number_input("Total SKS Kumulatif (Smt 1-4)", min_value=0, max_value=100, value=80)
    jk_input = st.selectbox("Jenis Kelamin", options=["Laki-laki", "Perempuan"])
    
    # Konversi Jenis kelamin agar sesuai data asli (L=1, P=0)
    jk = 1 if jk_input == "Laki-laki" else 0

st.markdown("---")

# -----------------------------------------
# 5. TOMBOL PREDIKSI & LOGIKA AI
# -----------------------------------------
if st.button("🚀 Prediksi Status Kelulusan", use_container_width=True):
    
    # Animasi loading biar terlihat keren
    with st.spinner('AI sedang menganalisis pola akademik mahasiswa...'):
        # Meminta AI menebak (1 = Aman, 0 = Bahaya DO)
        prediksi, probabilitas = predict_cached(
            ips1=ips1,
            ips2=ips2,
            ips3=ips3,
            ips4=ips4,
            sks=sks,
            jk=jk,
        )
        
    st.markdown("### HASIL PREDIKSI:")
    
    # Menampilkan hasil dengan warna (Hijau = Aman, Merah = Bahaya)
    if prediksi == 1:
        st.success("✅ **STATUS AMAN: Lulus Tepat Waktu**")
        st.write(f"Tingkat keyakinan AI: **{probabilitas[1] * 100:.2f}%** mahasiswa ini akan lulus sesuai target.")
        st.info("Saran: Pertahankan nilai IPS dan segera ambil mata kuliah skripsi di waktu yang tepat.")
    else:
        st.error("🚨 **STATUS BAHAYA: Potensi Terlambat Lulus / DO**")
        st.write(f"Tingkat keyakinan AI: **{probabilitas[0] * 100:.2f}%** mahasiswa ini berisiko melampaui batas waktu kelulusan.")
        st.warning("Tindakan Preventif (EWS): Dosen Pembimbing Akademik harus segera memanggil mahasiswa ini untuk konsultasi, periksa apakah ada hambatan pribadi, finansial, atau mata kuliah bersyarat yang belum lulus.")
