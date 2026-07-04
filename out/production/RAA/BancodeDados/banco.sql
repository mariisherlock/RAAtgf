DROP DATABASE IF EXISTS RAA;
CREATE DATABASE RAA;
USE RAA;

CREATE TABLE campus(
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL
);

CREATE TABLE cidade(
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL
);

CREATE TABLE curso(
                      id  INT AUTO_INCREMENT PRIMARY KEY,
                      nome VARCHAR(100) NOT NULL,
                      campus_id INT NOT NULL,
                      FOREIGN KEY(campus_id) REFERENCES campus(id)
);

CREATE TABLE pessoa(
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL
);

CREATE TABLE usuario(
                        pessoa_id INT PRIMARY KEY,
                        email VARCHAR(150) NOT NULL UNIQUE,
                        senha VARCHAR(255) NOT NULL,
                        tipo ENUM('ALUNO', 'ADMINISTRADOR', 'VOLUNTARIO') NOT NULL,
                        FOREIGN KEY(pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
);

CREATE TABLE aluno(
                      usuario_id INT NOT NULL PRIMARY KEY,
                      data_nascimento DATE NOT NULL,
                      cidade_id INT NOT NULL,
                      curso_id INT NOT NULL,
                      FOREIGN KEY(usuario_id) REFERENCES usuario(pessoa_id) ON DELETE CASCADE,
                      FOREIGN KEY(cidade_id) REFERENCES cidade(id),
                      FOREIGN KEY(curso_id) REFERENCES curso(id)
);

CREATE TABLE administrador(
                              usuario_id INT NOT NULL PRIMARY KEY,
                              FOREIGN KEY(usuario_id) REFERENCES usuario(pessoa_id) ON DELETE CASCADE
);

CREATE TABLE voluntario(
                           usuario_id INT NOT NULL PRIMARY KEY,
                           contato VARCHAR(150),
                           FOREIGN KEY(usuario_id) REFERENCES usuario(pessoa_id) ON DELETE CASCADE
);

CREATE TABLE relato(
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       titulo VARCHAR(200) NOT NULL,
                       descricao TEXT NOT NULL,
                       local VARCHAR(200) NOT NULL,
                       data DATE NOT NULL,
                       usuario_anonimo BOOLEAN DEFAULT FALSE,
                       categoria ENUM('ASSEDIO_MORAL', 'ASSEDIO_SEXUAL', 'DISCRIMINACAO', 'OPRESSAO', 'OUTRO') NOT NULL,
                       status ENUM('PENDENTE', 'EM_ANALISE', 'APROVADO') DEFAULT 'PENDENTE',
                       autor_id INT NOT NULL,
                       FOREIGN KEY(autor_id) REFERENCES aluno(usuario_id)
);

CREATE TABLE comentario(
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           texto TEXT NOT NULL,
                           anonimo BOOLEAN DEFAULT FALSE,
                           data DATE NOT NULL,
                           autor_id INT NOT NULL,
                           relato_id INT NOT NULL,
                           FOREIGN KEY(autor_id) REFERENCES aluno(usuario_id) ON DELETE CASCADE,
                           FOREIGN KEY(relato_id) REFERENCES relato(id) ON DELETE CASCADE
);