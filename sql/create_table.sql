#数据库初始化

create
    database if not exists rocMarket;

use rocMarket;

-- 修改数据库字符集为 utf8mb4
ALTER DATABASE `rocmarket` CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;


-- Users table with columns in snake_case format
CREATE TABLE Users (
                       id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key',
                       username VARCHAR(255) NOT NULL UNIQUE COMMENT 'User nickname',
                       email VARCHAR(255) NOT NULL UNIQUE COMMENT 'User email',
                       encrypted_password VARCHAR(255) NOT NULL COMMENT 'Encrypted password',

                       bio TEXT COMMENT 'User bio/description',
                       personal_website VARCHAR(255) COMMENT 'User personal website',
                       contact_info VARCHAR(255) COMMENT 'User contact information',

                       user_status INT DEFAULT 0 NOT NULL COMMENT 'Status: 0 - Active, 1 - Banned',
                       user_role INT DEFAULT 0 NOT NULL COMMENT 'User role: 0 - Regular User, 1 - Administrator',

                       is_deleted TINYINT(1) DEFAULT 0 COMMENT 'Soft delete flag',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp',
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update timestamp'
);


-- Professors table
CREATE TABLE Professors (
                            id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key',
                            name VARCHAR(255) NOT NULL COMMENT 'Professor name',

                            average_grade DECIMAL(4, 2) COMMENT 'Average grade given by the professor',

                            is_deleted TINYINT(1) DEFAULT 0 COMMENT 'Soft delete flag',
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp',
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update timestamp'
);

-- Courses table
CREATE TABLE Courses (
                         id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key',
                         course_code VARCHAR(255) NOT NULL COMMENT 'Course code',
                         course_name VARCHAR(255) NOT NULL COMMENT 'Course name',
                         professor_id INT not null COMMENT 'Foreign key referencing Professors table',
                         credits TINYINT COMMENT 'Number of credits',

                         difficulty TINYINT(1) COMMENT 'Difficulty level of the course, 1=简单, 2=中等, 3=困难',
                         homework_amount TINYINT(1) COMMENT 'Amount of homework, 1=很少, 2=中等, 3=超多',
                         grading_fairness TINYINT(1) COMMENT 'Fairness or leniency of grading, 1=超坏, 2=一般, 3=超好',
                         learning_gain TINYINT(1) COMMENT 'Overall learning gain or experience, 1=很少, 2=一般, 3=很多',

                         average_score DECIMAL(4, 2) COMMENT 'Average score of the course',
                         number_of_reviews INT COMMENT 'Number of reviews',

                         is_deleted TINYINT(1) DEFAULT 0 COMMENT 'Soft delete flag',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp',
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update timestamp',
                         FOREIGN KEY (professor_id) REFERENCES Professors(id)
);

CREATE TABLE Semesters (
                           id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key, auto-incremented',
                           semester_name VARCHAR(20) NOT NULL COMMENT 'Semester name, e.g., 2024春',
                           UNIQUE (semester_name) COMMENT 'Ensure unique semester names'
);

CREATE TABLE Course_Semesters (
                                  id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key, auto-incremented',
                                  course_id INT COMMENT 'Foreign key referencing Courses table',
                                  semester_id INT COMMENT 'Foreign key referencing Semesters table',
                                  FOREIGN KEY (course_id) REFERENCES Courses(id) ON DELETE CASCADE,
                                  FOREIGN KEY (semester_id) REFERENCES Semesters(id) ON DELETE CASCADE
);



-- Reviews table with a foreign key to Semesters table
CREATE TABLE Reviews (
                         id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key',
                         user_id INT NOT NULL COMMENT 'Foreign key referencing Users table',
                         course_id INT NOT NULL COMMENT 'Foreign key referencing Courses table',
                         semester_id INT NOT NULL COMMENT 'Foreign key referencing Semesters table',

                         score DECIMAL(4, 2) COMMENT 'Score given in the review 1-5',
                         difficulty TINYINT(1) COMMENT 'Difficulty of the course, 1=低, 2=中等, 3=高',
                         homework_amount TINYINT(1) COMMENT 'Amount of homework, 1=少, 2=中等, 3=很多',
                         grading_quality TINYINT(1) COMMENT 'Quality of grading, 1=严苛, 2=一般, 3=宽松',
                         learning_gain TINYINT(1) COMMENT 'Overall learning gain or experience, 1=很少, 2=一般, 3=很多',
                         review TEXT COMMENT 'Review or comments on the course',

                         is_deleted TINYINT(1) DEFAULT 0 COMMENT 'Soft delete flag',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp',
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update timestamp',

                         FOREIGN KEY (user_id) REFERENCES Users(id),
                         FOREIGN KEY (course_id) REFERENCES Courses(id),
                         FOREIGN KEY (semester_id) REFERENCES Semesters(id)
);


-- -----------------------------------------------------------------------------
-- 修改表字符集为 utf8mb4
ALTER TABLE `roc_user` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE roc_user
(
    id           BIGINT AUTO_INCREMENT             NOT NULL COMMENT '用户ID',
    nick_name VARCHAR(256) NOT NULL DEFAULT '匿名' COMMENT '用户昵称',
    email        VARCHAR(512)                       NOT NULL UNIQUE COMMENT '邮箱',
    password     VARCHAR(512)                       NOT NULL COMMENT '密码',
    status       INT      DEFAULT 0                 NOT NULL COMMENT '状态 (0 - 正常, 1 - 封禁)',
    create_time  DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time  DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    user_role    INT      DEFAULT 0                 NOT NULL COMMENT '用户角色 (0 - 用户, 1 - 管理员, 2 - VIP)',
    tags         JSON                               NULL COMMENT '标签 JSON 列表',
    contact_info VARCHAR(1024)                      NULL COMMENT '联系方式',
    is_delete    TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否删除'
) COMMENT '用户信息表';

ALTER TABLE roc_user
    ADD COLUMN is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除';

ALTER TABLE roc_user
    ADD COLUMN id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;

ALTER TABLE roc_user
    MODIFY COLUMN nick_name VARCHAR(256) NOT NULL DEFAULT '匿名' COMMENT '用户昵称';
-- 用户表
create table user
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    planetCode   varchar(512)                       null comment '星球编号',
    tags         varchar(1024)                      null comment '标签 json 列表'
) comment '用户';

-- 队伍表
create table team
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(256)                       not null comment '队伍名称',
    description varchar(1024)                      null comment '描述',
    maxNum      int      default 1                 not null comment '最大人数',
    expireTime  datetime                           null comment '过期时间',
    userId      bigint comment '用户id（队长 id）',
    status      int      default 0                 not null comment '0 - 公开，1 - 私有，2 - 加密',
    password    varchar(512)                       null comment '密码',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete    tinyint  default 0                 not null comment '是否删除'
) comment '队伍';

-- 用户队伍关系
create table user_team
(
    id         bigint auto_increment comment 'id'
        primary key,
    userId     bigint comment '用户id',
    teamId     bigint comment '队伍id',
    joinTime   datetime                           null comment '加入时间',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '用户队伍关系';


-- 标签表（可以不创建，因为标签字段已经放到了用户表中）
create table tag
(
    id         bigint auto_increment comment 'id'
        primary key,
    tagName    varchar(256)                       null comment '标签名称',
    userId     bigint                             null comment '用户 id',
    parentId   bigint                             null comment '父标签 id',
    isParent   tinyint                            null comment '0 - 不是, 1 - 父标签',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除',
    constraint uniIdx_tagName
        unique (tagName)
) comment '标签';


create index idx_userId
    on tag (userId);