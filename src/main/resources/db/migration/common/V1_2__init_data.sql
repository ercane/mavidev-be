CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO country(id, code, name, phone_code, status)
VALUES ('11568584-7721-4be3-acc8-c73f4f8cfdd1', 'TR', 'Türkiye', '90', 'ACTIVE');

INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'ADANA');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'ADIYAMAN');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'AFYONKARAHİSAR');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'AĞRI');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'AMASYA');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'ANKARA');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'ANTALYA');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'ARTVİN');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'AYDIN');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'BALIKESİR');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'BİLECİK');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'BİNGÖL');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'BİTLİS');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'BOLU');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'BURDUR');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'BURSA');
INSERT INTO CITY (id, country_id, name)
VALUES (uuid_generate_v4(), '11568584-7721-4be3-acc8-c73f4f8cfdd1', 'ÇANAKKALE');

update city
set status='ACTIVE',
    code=id
where id != '11568584-7721-4be3-acc8-c73f4f8cfdd1';