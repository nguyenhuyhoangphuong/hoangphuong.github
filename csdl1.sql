use csdl1
go
create table tk(
idtk int identity (1000,1) primary key,
hoten nvarchar(100),
taikhoan nvarchar(100),
mk nvarchar(100),
sdt nvarchar(100),
diachi nvarchar(100))
go
drop table tk
go
create table danhmuc_sp(
    iddm int identity (1000,1) primary key ,
    tendm nvarchar(100),
    hinhanh nvarchar(100),
	ghichu nvarchar(100)
)
go
create table chon(
    id_l int identity (1000,1) primary key ,
    the_loai nvarchar(100),
    ten nvarchar(100),
	tien nvarchar(100),
	ghichu nvarchar(100)
)
drop table chon
go
create table san_pham(
    idsp int identity (1000,1) primary key ,
	iddm int FOREIGN KEY REFERENCES danhmuc_sp(iddm),
    tensp nvarchar(100),
    giatien nvarchar(100),
	hinhanh nvarchar(100),
	ghichu nvarchar(100)
)
go
create table giohang(
    idgh int identity (1000,1) primary key ,
	idtk int FOREIGN KEY REFERENCES tk(idtk),
	idsp int FOREIGN KEY REFERENCES san_pham(idsp),
    sl int,
	chon nvarchar(100),
	giax1 nvarchar(100),
    tongtien int,
	ghichu nvarchar(100)
)
drop table giohang

go
create table donhang(
    iddh int identity (1000,1) primary key ,
	idtk int FOREIGN KEY REFERENCES tk(idtk),
	idgh int FOREIGN KEY REFERENCES giohang(idgh),
    loinhan nvarchar(100),
	ngaygio nvarchar(100),	
    hanhchinh nvarchar(100),
	ghichu nvarchar(100)
)
