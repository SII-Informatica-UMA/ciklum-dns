create sequence dieta_seq start with 1 increment by 50;
create sequence usuario_seq start with 1 increment by 50;
create table dieta (duracion_dias integer, entrenador_fk integer, id integer not null, descripcion varchar(255), nombre varchar(255), objetivo varchar(255), observaciones varchar(255), recomendaciones varchar(255), alimentos varchar(255) array, primary key (id));
create table usuario (administrador boolean, dieta_fk integer, fecha_alta date, fecha_baja date, fecha_nacimiento date, id integer not null, id_centro integer, rol char(1) not null, apellido1 varchar(255), apellido2 varchar(255), direccion varchar(255), dni varchar(255), email varchar(255), especialidad varchar(255), experiencia varchar(255), nombre varchar(255), observaciones varchar(255), password varchar(255), sexo varchar(255) check (sexo in ('HOMBRE','MUJER','OTRO')), telefono varchar(255), titulacion varchar(255), primary key (id));
alter table if exists dieta add constraint FK3f22sxvmfwann13si4vrvr9u1 foreign key (entrenador_fk) references usuario;
alter table if exists usuario add constraint FKsy9h594ee3lr7rcf94mwe8esy foreign key (dieta_fk) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create sequence usuario_seq start with 1 increment by 50;
create table dieta (duracion_dias integer, entrenador_fk integer, id integer not null, descripcion varchar(255), nombre varchar(255), objetivo varchar(255), observaciones varchar(255), recomendaciones varchar(255), alimentos varchar(255) array, primary key (id));
create table usuario (administrador boolean, dieta_fk integer, fecha_alta date, fecha_baja date, fecha_nacimiento date, id integer not null, id_centro integer, rol char(1) not null, apellido1 varchar(255), apellido2 varchar(255), direccion varchar(255), dni varchar(255), email varchar(255), especialidad varchar(255), experiencia varchar(255), nombre varchar(255), observaciones varchar(255), password varchar(255), sexo varchar(255) check (sexo in ('HOMBRE','MUJER','OTRO')), telefono varchar(255), titulacion varchar(255), primary key (id));
alter table if exists dieta add constraint FK3f22sxvmfwann13si4vrvr9u1 foreign key (entrenador_fk) references usuario;
alter table if exists usuario add constraint FKsy9h594ee3lr7rcf94mwe8esy foreign key (dieta_fk) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create sequence usuario_seq start with 1 increment by 50;
create table dieta (duracion_dias integer, entrenador_fk integer, id integer not null, descripcion varchar(255), nombre varchar(255), objetivo varchar(255), observaciones varchar(255), recomendaciones varchar(255), alimentos varchar(255) array, primary key (id));
create table usuario (administrador boolean, dieta_fk integer, fecha_alta date, fecha_baja date, fecha_nacimiento date, id integer not null, id_centro integer, rol char(1) not null, apellido1 varchar(255), apellido2 varchar(255), direccion varchar(255), dni varchar(255), email varchar(255), especialidad varchar(255), experiencia varchar(255), nombre varchar(255), observaciones varchar(255), password varchar(255), sexo varchar(255) check (sexo in ('HOMBRE','MUJER','OTRO')), telefono varchar(255), titulacion varchar(255), primary key (id));
alter table if exists dieta add constraint FK3f22sxvmfwann13si4vrvr9u1 foreign key (entrenador_fk) references usuario;
alter table if exists usuario add constraint FKsy9h594ee3lr7rcf94mwe8esy foreign key (dieta_fk) references dieta;
create sequence dieta_seq start with 1 increment by 50;
create table dieta (duracion_dias integer, entrenador bigint, id bigint not null, descripcion varchar(255), nombre varchar(255), objetivo varchar(255), observaciones varchar(255), recomendaciones varchar(255), alimentos varchar(255) array, primary key (id));
create table dieta_clientes (clientes bigint, dieta_id bigint not null);
alter table if exists dieta_clientes add constraint FKst73eerfaasmdsibku92bv5o5 foreign key (dieta_id) references dieta;
