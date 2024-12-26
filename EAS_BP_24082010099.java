package com.mycompany.latihanjava;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class EAS_BP_24082010099 {
    static Scanner input = new Scanner(System.in);
    
    static int hari = getHariKe(LocalDate.now().getDayOfWeek());
    static int jumlah = 0, total = 0, grandTotal = 0, baris = 0, kolom = 0; //keperluan transaksi
    static int pinKasir = 1111, pinAdmin = 2222, pinOwner = 3333; //pin
    static double harga = 0, bayar = 0, kembalian = 0, diskon, uangBayar, totalPemasukanOwner = 0; //kasir dan owner
    
    static String produk[] = new String[10]; //keranjang
    static int nota[][]    = new int [10][3];
    
    //array menu, kategori, harga, jumlah laku, dan laporan harian
    static String menu[][] = {
        {"Indomie_Goreng", "Mie"},
        {"Indomie_Rendang", "Mie"},
        {"Indomie_Aceh", "Mie"},
        {"Indomie_Ayam_Bawang", "Mie"},
        {"Es_Teh", "Minuman_Dingin"},
        {"Nutrisari_Jeruk", "Minuman_Dingin"},
        {"Milo", "Minuman_Dingin"},
        {"Teh_Hangat", "Minuman_Hangat"},
        {"GoodDay", "Minuman_Hangat"},
        {"Susu_Putih", "Minuman_Hangat"}
    };
    static int hargaMenu[] = {7000, 7000, 7000, 7000, 8000, 12000, 12000, 8000, 12000, 7000};
    static int jumlahLaku[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[][] laporanHarian = new int[menu.length][7];
    
    //format int ke rupiah
    public static String formatRupiah(double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        format.setMaximumFractionDigits(0);
        return format.format(amount);
    }
    
    //get data semua submenu
    static void getData(String data[][], int harga[]){
        System.out.println("\n============================================================");
        System.out.println("                         Daftar Menu                        ");
        System.out.println("============================================================");
        System.out.printf("%-4s %-20s %-20s %-10s%n", "No", "Menu", "Kategori", "Harga");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%-4d %-20s %-20s %-10s%n", (i + 1), data[i][0],  data[i][1], formatRupiah(harga[i]));
        }
        System.out.println();
    }
    
    //tambah menu ke keranjang
    static void tambahKeranjang(int pilihMenus, int jumlahMenus){
        jumlahLaku[pilihMenus] += jumlahMenus;
        laporanHarian[pilihMenus][hari - 1] += jumlahMenus;
        produk[baris] = menu[pilihMenus][0];
        nota[baris][0] = hargaMenu[pilihMenus];
        nota[baris][1] = jumlah;
        nota[baris][2] = nota[baris][0] * nota[baris][1];
        grandTotal += nota[baris][2];
        baris++;
    }
    
    //menampilkan keraanjang
    static void tampilkanKeranjang() {
        System.out.println("\nNota Belanja");
        System.out.printf("%-5s %-20s %-10s %-10s %-10s%n", "No", "Menu", "Harga", "Jumlah", "Total");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < baris; i++) {
            System.out.printf("%-5d %-20s %-10s %-10d %-10s%n", (i + 1), produk[i], formatRupiah(nota[i][0]), nota[i][1], formatRupiah(nota[i][2]));
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Total Belanja : " + formatRupiah(grandTotal)+ "\n");
    }
    
    //bersihkan keranjang
    static void clearKeranjang() {
        baris = 0;
        for (int i = 0; i < produk.length; i++) {
            produk[i] = null;
        }
        for (int i = 0; i < nota.length; i++) {
            for (int j = 0; j < nota[i].length; j++) {
                nota[i][j] = 0;
            }
        }
    }
    
    //cari sub menu dan return index
    public static int searchMenu(String namaMenu, String[][] menu) {
        for (int i = 0; i < menu.length; i++) {
            if (namaMenu.equalsIgnoreCase(menu[i][0])) {
                return i;
            }
        }
        return -1;
    }
    
    //pembayaran
    static void bayar(int grandTotalMethod){
        if(produk.length != 0){
            System.out.println("=============================================================");
             System.out.print("Uang Yang Di Bayar : ");
            uangBayar = input.nextDouble();

            if (uangBayar == grandTotal) {
                System.out.println("Uang Yang Di Bayarkan Pas");
                clearKeranjang();
            } else if (uangBayar > grandTotal){
                kembalian = uangBayar - grandTotal;
                System.out.println("Kembalian : " + formatRupiah(kembalian));
                clearKeranjang();
            } else {
                System.out.println("Uang Yang Dibayarkan Kurang");
            }
            totalPemasukanOwner += grandTotal;
        } else{
            System.out.println("Keranjang anda kosong !");
        }
        System.out.println();
    }
    
    //tambah menu dan kategori
    static String[][] tambahMenuDanKategori(String[][] menu, String menuBaru, String kategoriBaru) {
        String[][] newMenu = new String[menu.length + 1][2];
        System.arraycopy(menu, 0, newMenu, 0, menu.length);
        newMenu[menu.length] = new String[] {menuBaru, kategoriBaru};
        return newMenu;
    }

    // Tambah harga
    static int[] tambahHarga(int[] hargaMenu, int hargaBaru) {
        int[] newHarga = new int[hargaMenu.length + 1];
        System.arraycopy(hargaMenu, 0, newHarga, 0, hargaMenu.length);
        newHarga[hargaMenu.length] = hargaBaru;
        return newHarga;
    }

    // Tambah jumlah laku dengan default 0
    static int[] tambahJumlahLaku(int[] jumlahLaku) {
        int[] newJumlahLaku = new int[jumlahLaku.length + 1];
        System.arraycopy(jumlahLaku, 0, newJumlahLaku, 0, jumlahLaku.length);
        newJumlahLaku[jumlahLaku.length] = 0;
        return newJumlahLaku;
    }
    
    //tambah laporan harian
    static int[][] tambahLaporanHarian(int[][] laporanHarian) {
        int[][] newLaporan = new int[laporanHarian.length + 1][7];
        for (int i = 0; i < laporanHarian.length; i++) {
            System.arraycopy(laporanHarian[i], 0, newLaporan[i], 0, laporanHarian[i].length);
        }
        for (int i = 0; i < 7; i++) {
            newLaporan[newLaporan.length - 1][i] = 0;
        }
        return newLaporan;
    }
    
    //hapus menu dan kategori
    static String[][] hapusMenuDanKategori(String[][] menu, int index) {
        String[][] newMenu = new String[menu.length - 1][2];
        System.arraycopy(menu, 0, newMenu, 0, index);
        System.arraycopy(menu, index + 1, newMenu, index, menu.length - index - 1);

        return newMenu;
    }
    
    //hapus harga
    static int[] hapusHarga(int[] hargaMenu, int index){
        int[] newHarga=new int[hargaMenu.length-1];
        for (int i = 0, j=0; i < hargaMenu.length; i++) {
            if (i!=index) {
                newHarga[j++]=hargaMenu[i];
            }
        }
        return newHarga;
    }
    
    //hapus jumlah laku
    static int[] hapusJumlahLaku(int[] jumlahLaku, int index){
        int[] newJumlahLaku=new int[jumlahLaku.length-1];
        for (int i = 0, j=0; i < jumlahLaku.length; i++) {
            if (i!=index) {
                newJumlahLaku[j++]=jumlahLaku[i];
            }
        }
        return newJumlahLaku;
    }
    
    //hapus laporan harian
    static int[][] hapusLaporanHarian(int[][] laporanHarian, int index) {
        int[][] newLaporanHarian = new int[laporanHarian.length - 1][laporanHarian[0].length];
        for (int i = 0, j = 0; i < laporanHarian.length; i++) {
            if (i != index) {
                newLaporanHarian[j++] = laporanHarian[i];
            }
        }
        return newLaporanHarian;
    }

     
    //total pemasukan
     static void totalPemasukan(double totalPemasukan){
        System.out.println("\nTotal Pemasukan : " + formatRupiah(totalPemasukan));
    }
     
    //sorting selection sort dari yang tertinggi
    static void selectionSort(String menuSort[][], int hargaSort[], int pemasukanSort[]){
        for (int i = 0; i < menuSort.length-1; i++) {
            for (int j = i+1; j < menuSort.length; j++) {
                if (pemasukanSort[i] < pemasukanSort[j]) {
                    int temp = pemasukanSort[j];
                    pemasukanSort[j] = pemasukanSort[i];
                    pemasukanSort[i] = temp;
                    
                    String temp2 = menuSort[j][0];
                    menuSort[j][0] = menuSort[i][0];
                    menuSort[i][0] = temp2;
                    
                    String temp3 = menuSort[j][1];
                    menuSort[j][1] = menuSort[i][1];
                    menuSort[i][1] = temp3;
                    
                    int temp4 = hargaSort[j];
                    hargaSort[j] = hargaSort[i];
                    hargaSort[i] = temp4;
                }
            }
        }
        System.out.println("\n5 Menu Terlaris:");
        System.out.printf("%-5s %-20s %-10s%n", "No", "Menu", "Jumlah Terjual");
        System.out.println("=========================================");
        for (int i = 0; i < 5; i++) {
            System.out.printf("%-5d %-20s %-10d%n", (i + 1), menuSort[i][0], pemasukanSort[i]);
        }
        System.out.println();
    }
    
    //get data per hari laporan harian
    static void getDataPerHari(int hari, String[][] namaMenu, int[] hargaProduk) {
        System.out.println("\nLaporan Penjualan Hari " + getNamaHari(hari));
        System.out.printf("%-5s %-20s %-10s %-10s %-10s%n", "No", "Menu", "Harga", "Jumlah", "Total");
        System.out.println("-------------------------------------------------------------");
        int totalPemasukanHarian = 0;
        int totalJumlahHarian = 0;
        int no = 1;
        for (int i = 0; i < namaMenu.length; i++) {
            if (laporanHarian[i][hari - 1] > 0) {
                int jumlahTerjual = laporanHarian[i][hari - 1];
                int pemasukan = jumlahTerjual * hargaProduk[i];
                totalJumlahHarian += jumlahTerjual;
                totalPemasukanHarian += pemasukan;
                System.out.printf("%-5d %-20s %-10s %-10d %-10s%n", (no++), namaMenu[i][0], formatRupiah(hargaProduk[i]), jumlahTerjual, formatRupiah(pemasukan));
            }
        }
        System.out.println("-------------------------------------------------------------");
        System.out.printf("Total Jumlah Terjual: %d%n", totalJumlahHarian);
        System.out.printf("Total Pemasukan: %s%n", formatRupiah(totalPemasukanHarian));
        System.out.println();
    }
    
    //get hari otomatis dan konversi ke int 1-7
    public static int getHariKe(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return 1;  // Senin
            case TUESDAY: return 2; // Selasa
            case WEDNESDAY: return 3; // Rabu
            case THURSDAY: return 4; // Kamis
            case FRIDAY: return 5;  // Jumat
            case SATURDAY: return 6; // Sabtu
            case SUNDAY: return 7;  // Minggu
            default: return 0;
        }
    }
    
    //konversi hari dari int ke nama-nama hari
    public static String getNamaHari(int hari) {
    switch (hari) {
        case 1: return "Senin";
        case 2: return "Selasa";
        case 3: return "Rabu";
        case 4: return "Kamis";
        case 5: return "Jumat";
        case 6: return "Sabtu";
        case 7: return "Minggu";
        default: return "Tidak Valid";
    }
}
    
    public static void main(String[] args) {
        int pilihLevelProgram = 0, pilihFungsi = 0;
        int pilihMenu = 0,  pilihKategori = 0; //kasir
        
        do{
            System.out.println("==== Pogram Kasir Sederhana Final Project EAS UMKM Kedai Kopi Temeji ====\n");
            System.out.println("1. Aplikasi Kasir\n2. Aplikasi Admin\n3. Aplikasi Owner\n4. Exit\n");
            
            System.out.print("Pilih Level anda : ");
            pilihLevelProgram = input.nextInt();
            
             switch(pilihLevelProgram) {                         
                 
                //kasir
                case 1:
                    int ulangTransaksi = 0;
                    System.out.println("\n==== Progam Kasir Level Kasir ====\n");
                    
                    System.out.print("Masukkan Pin Kasir : ");
                    int pinKasirInput = input.nextInt();
                    System.out.println();
                    
                    if (pinKasir == pinKasirInput){
                        System.out.println("Pin anda benar selamat datang !\n");
                        do{
                            System.out.println("Daftar Fungsi");
                            System.out.println("1. Tampilkan Menu");
                            System.out.println("2. Transaksi");
                            System.out.println("3. Cari");
                            System.out.println("4. Kembali");

                            System.out.print("\nPilih : ");
                            pilihFungsi = input.nextInt();
                            
                            switch(pilihFungsi){
                                case 1:
                                    getData(menu, hargaMenu);
                                break;
                                
                                case 2:
                                    grandTotal = 0;
                                    do{
                                        getData(menu, hargaMenu);
                                        System.out.print("Pilih Menu Yang Akan Dibeli : ");
                                        pilihMenu = input.nextInt();

                                        if (pilihMenu == 0){
                                            break;
                                        } else if(pilihMenu >=1 && pilihMenu <= menu.length){
                                            System.out.print("Jumlah : ");
                                            jumlah = input.nextInt();

                                            tambahKeranjang(pilihMenu-1, jumlah);

                                            System.out.print("Tambah Transaksi? [y/n] : ");
                                            String lanjutTransaksi = input.next();
                                            if(lanjutTransaksi.equalsIgnoreCase("y")){
                                                ulangTransaksi = 1;
                                            } else {
                                                ulangTransaksi = 0;
                                            }
                                        } else {
                                            System.out.println("Pilihan anda tidak valid. \n");
                                        }

                                        if (ulangTransaksi == 0){
                                            tampilkanKeranjang();
                                            bayar(grandTotal);
                                        }
                                    }while(ulangTransaksi != 0);
                                break;
                                
                                case 3:
                                    grandTotal = 0;
                                    do{
                                        System.out.print("\nNama Menu : ");
                                        String namaMenuKasir = input.next();
                                        System.out.println();

                                        int indexSearch = searchMenu(namaMenuKasir, menu);
                                        if (indexSearch == -1){
                                            System.out.println("Menu tidak ditemukan!\n");
                                        } else if(indexSearch >=1 && indexSearch <= menu.length){
                                            System.out.println("Hasil Pencarian :");
                                            System.out.println(menu[indexSearch][0] + "\t" + menu[indexSearch][1] + "\t" + formatRupiah(hargaMenu[indexSearch]));
                                            System.out.print("Jumlah : ");
                                            jumlah = input.nextInt();

                                            tambahKeranjang(indexSearch, jumlah);

                                            System.out.print("Cari Menu Lain? [y/n] : ");
                                            String lanjutTransaksi = input.next();
                                            if(lanjutTransaksi.equalsIgnoreCase("y")){
                                                ulangTransaksi = 1;
                                            } else {
                                                ulangTransaksi = 0;
                                            }
                                            
                                            if (ulangTransaksi == 0){
                                                tampilkanKeranjang();
                                                bayar(grandTotal);
                                            }
                                        } else {
                                            System.out.println("Pilihan anda tidak valid. \n");
                                        }
                                   }while(ulangTransaksi != 0);
                                break;
                                
                                case 4:
                                break;
                                
                                default:
                                    System.out.println("Pilihan Anda Tidak Valid !\n");
                            }
                        }while(pilihFungsi != 4);
                    } else {
                        System.out.println("PIN anda salah akses ditolak.\n");
                    } 
                break;
                
                //admin
                case 2:
                    System.out.println("\n==== Progam Kasir Level Admin ====\n");
                    
                    System.out.print("Masukkan Pin Admin : ");
                    int pinAdminInput = input.nextInt();
                    System.out.println();
                    
                    if (pinAdmin == pinAdminInput){
                        System.out.println("Pin anda benar selamat datang !\n");
                        do {
                            System.out.println("Daftar Fungsi");
                            System.out.println("1. Lihat Semua Menu");
                            System.out.println("2. Tambah Menu");
                            System.out.println("3. Ubah Menu");
                            System.out.println("4. Hapus Menu");
                            System.out.println("5. Selesai");

                            System.out.print("\nPilih : ");
                            pilihFungsi = input.nextInt();
                            System.out.println();

                            switch(pilihFungsi){
                                case 1:
                                    getData(menu, hargaMenu);
                                break;

                                case 2:
                                    System.out.print("Masukkan nama menu baru: ");
                                    String menuBaru = input.next();
                                    System.out.println("1. Mie\n2. Minuman Dingin\n3. Minuman Hangat");
                                    System.out.print("Pilih Kategori baru: ");
                                    int kategoriSangatBaru = input.nextInt();

                                    String kategoriBaru = "";
                                    if (kategoriSangatBaru == 1){
                                        kategoriBaru = "Mie";
                                    } else if (kategoriSangatBaru == 2) {
                                        kategoriBaru = "Minuman_Dingin";
                                    }   else if (kategoriSangatBaru == 3) {
                                        kategoriBaru = "Minuman_Hangat";
                                    } else {
                                        System.out.println("Pilihan anda tidak valid !");
                                    }

                                    System.out.print("Masukkan harga menu baru: ");
                                    int hargaBaru = input.nextInt();
                                    System.out.println();

                                    menu = tambahMenuDanKategori(menu, menuBaru, kategoriBaru);
                                    hargaMenu=tambahHarga(hargaMenu, hargaBaru);
                                    jumlahLaku = tambahJumlahLaku(jumlahLaku);
                                    laporanHarian = tambahLaporanHarian(laporanHarian);
                                    
                                    System.out.println("Menu baru berhasil ditambahkan !\n");
                                break;

                                case 3:
                                    System.out.println("Cari Menu !");
                                    System.out.print("Nama Menu : ");
                                    String namaMenu = input.next();

                                    int indexSearch = searchMenu(namaMenu, menu);

                                    if (indexSearch != -1) {
                                        do{
                                            System.out.println("\nHasil Pencarian :");
                                            System.out.println(menu[indexSearch][0] + "\t" + menu[indexSearch][1] + "\t" + formatRupiah(hargaMenu[indexSearch]) + "\n");

                                            System.out.println("1. Ubah Nama");
                                            System.out.println("2. Ubah Kategori");
                                            System.out.println("3. Ubah Harga");
                                            System.out.println("4. Kembali");

                                            System.out.print("Pilih : ");
                                            pilihKategori = input.nextInt();
                                            System.out.println();

                                            switch(pilihKategori){
                                                case 1:
                                                    System.out.println("Nama Lama : " + menu[indexSearch][0]);
                                                    System.out.print("Nama Baru : ");
                                                    menu[indexSearch][0] = input.next();
                                                    System.out.println("Nama menu berhasil diubah !");
                                                break;

                                                case 2:
                                                    System.out.println("Kategori Lama : " + menu[indexSearch][1]);
                                                    System.out.println("1. Mie\n2. Minuman Dingin\n3. Minuman Hangat");
                                                    System.out.print("Pilih Kategori baru: ");
                                                    int kategoriTambahBaru= input.nextInt();

                                                    String kategoriTambah = "";
                                                    if (kategoriTambahBaru == 1){
                                                        kategoriTambah = "Mie";
                                                    } else if (kategoriTambahBaru == 2) {
                                                        kategoriTambah = "Minuman_Dingin";
                                                    }   else if (kategoriTambahBaru == 3) {
                                                        kategoriTambah = "Minuman_Hangat";
                                                    } else {
                                                        System.out.println("Pilihan anda tidak valid !");
                                                    }
                                                    menu[indexSearch][1] = kategoriTambah;
                                                    System.out.println("Kategori berhasil diubah !");
                                                break;

                                                case 3:
                                                    System.out.println("Harga Lama : " + formatRupiah(hargaMenu[indexSearch]));
                                                    System.out.print("Harga Baru : ");
                                                    hargaMenu[indexSearch] = input.nextInt();
                                                    System.out.println("Harga menu berhasil diubah !");
                                                break;
                                                
                                                case 4:
                                                break;

                                                default:
                                                    System.out.println("Pilihan Anda Tidak Valid !\n");
                                            }

                                        }while(pilihKategori != 4);
                                    } else {
                                        System.out.println("Menu tidak ditemukan.");
                                    }
                                break;

                                case 4:
                                    System.out.println("Cari Sub Menu !");
                                    System.out.print("Nama Sub Menu : ");
                                    String namaMenuHapus = input.next();

                                    int indexSearchHapus = searchMenu(namaMenuHapus, menu);

                                    if (indexSearchHapus != -1) {
                                        menu = hapusMenuDanKategori(menu, indexSearchHapus);
                                        hargaMenu = hapusHarga(hargaMenu, indexSearchHapus);
                                        jumlahLaku = hapusJumlahLaku(jumlahLaku, indexSearchHapus);
                                        laporanHarian = hapusLaporanHarian(laporanHarian, indexSearchHapus);

                                        System.out.println("Menu berhasil dihapus !\n");
                                    } else {
                                        System.out.println("Menu tidak ditemukan.");
                                    }
                                break;

                                case 5:
                                break;

                                default:
                                    System.out.println("Pilihan Anda Tidak Valid !\n");
                            }
                        }while(pilihFungsi != 5);
                    } else {
                        System.out.println("PIN anda salah akses ditolak.\n");
                    } 
                    
                    
                break;
                
                //owner
                case 3:
                    System.out.println("\n==== Progam Kasir Level Owner ====\n");
                    
                    System.out.print("Masukkan Pin Owner : ");
                    int pinOwnerInput = input.nextInt();
                    System.out.println();
                    
                    if (pinOwner == pinOwnerInput){
                        System.out.println("Pin anda benar selamat datang !\n");
                        do{
                            System.out.println("Daftar Fungsi");
                            System.out.println("1. Total Pemasukan");
                            System.out.println("2. 5 Menu Terlaris");
                            System.out.println("3. Laporan Harian");
                            System.out.println("4. Selesai");

                            System.out.print("\nPilih : ");
                            pilihFungsi = input.nextInt();
                            
                            switch(pilihFungsi){
                                case 1:
                                    totalPemasukan(totalPemasukanOwner);
                                    System.out.println();
                                break;
                                
                                case 2:
                                    selectionSort(menu, hargaMenu, jumlahLaku);
                                break;
                                        
                                case 3:
                                    System.out.println("\n1. Senin");
                                    System.out.println("2. Selasa");
                                    System.out.println("3. Rabu");
                                    System.out.println("4. Kamis");
                                    System.out.println("5. Jumat");
                                    System.out.println("6. Sabtu");
                                    System.out.println("7. Minggu");

                                    System.out.print("\nPilih : ");
                                    int pilihHari = input.nextInt();
                                    
                                    getDataPerHari(pilihHari, menu, hargaMenu);
                                break;
                                
                                case 4:
                                break;
                                
                                default:
                                    System.out.println("Pilihan Anda Tidak Valid !\n");
                            }
                        }while(pilihFungsi != 4);
                    } else {
                        System.out.println("PIN anda salah akses ditolak.\n");
                    } 
                break;
                
                case 4:
                break;
                
                default:
                    System.out.println("Pilihan Anda Tidak Valid!\n");
             }
            
        }while(pilihLevelProgram!=4);
    }
}