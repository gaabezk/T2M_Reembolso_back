insert into perfil (nome) values('ROLE_GESTOR');
insert into perfil (nome) values('ROLE_ADMINISTRATIVO');
insert into perfil (nome) values('ROLE_COLABORADOR');

insert into usuario (cpf, email, nome, senha, status) values ('31785878026','gestorreembolso@gmail.com','Gabriel Mello','$2a$10$y67Aqkacr46w12UU3trPnOYx0wx/kcAMmiY8.qOOugYHdA96n46bK','Ativo');
insert into usuario (cpf, email, nome, senha, status) values ('00892260033','responsaveladmreembolso@gmail.com','Ana Silva','$2a$10$y67Aqkacr46w12UU3trPnOYx0wx/kcAMmiY8.qOOugYHdA96n46bK','Ativo');
insert into usuario (cpf, email, nome, senha, status) values ('03958426034','colaboradorreembolso@gmail.com','Jorge Machado','$2a$10$y67Aqkacr46w12UU3trPnOYx0wx/kcAMmiY8.qOOugYHdA96n46bK','Ativo');

insert into usuario_perfil (data_criacao, id_perfil, id_usuario) values ('2022-07-17', 1, 1);
insert into usuario_perfil (data_criacao, id_perfil, id_usuario) values ('2022-07-17', 2, 2);
insert into usuario_perfil (data_criacao, id_perfil, id_usuario) values ('2022-07-17', 3, 3);

insert into categoria_despesa (nome) values ('Alimentação');
insert into categoria_despesa (nome) values ('Locomoção');
insert into categoria_despesa (nome) values ('Hospedagem');
insert into categoria_despesa (nome) values ('Material');

insert into cliente (cidade, estado, nome, status) values ('Petrópolis', 'RJ', 'T2M','Ativo');
insert into cliente (cidade, estado, nome, status) values ('Petrópolis', 'RJ', 'Best2Bee','Ativo');
insert into cliente (cidade, estado, nome, status) values ('Petrópolis', 'RJ', 'Serratec','Ativo');

