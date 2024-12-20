create table usuario (
	id uuid not null,
	nome varchar(255),
	email varchar(255),
	senha varchar(255)
);

alter table usuario add primary key (id);