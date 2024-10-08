PGDMP                      |            evaluasihum    16.3    16.3 M    $           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            %           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            &           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            '           1262    16398    evaluasihum    DATABASE     �   CREATE DATABASE evaluasihum WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_Indonesia.1252';
    DROP DATABASE evaluasihum;
                postgres    false            �            1259    25439    bobot_kriteria    TABLE     ~   CREATE TABLE public.bobot_kriteria (
    bobot integer,
    idbobot bigint NOT NULL,
    nmkriteria character varying(255)
);
 "   DROP TABLE public.bobot_kriteria;
       public         heap    postgres    false            �            1259    25426    bobotkriteria_sequence    SEQUENCE        CREATE SEQUENCE public.bobotkriteria_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.bobotkriteria_sequence;
       public          postgres    false            �            1259    25444    cpt    TABLE     Y  CREATE TABLE public.cpt (
    coverage integer NOT NULL,
    coveragepersen double precision NOT NULL,
    hitrate double precision NOT NULL,
    panolcustomer integer NOT NULL,
    penetration double precision NOT NULL,
    tahun integer NOT NULL,
    throughput double precision NOT NULL,
    idcpt bigint NOT NULL,
    nik bigint NOT NULL
);
    DROP TABLE public.cpt;
       public         heap    postgres    false            �            1259    25427    cpt_sequence    SEQUENCE     u   CREATE SEQUENCE public.cpt_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.cpt_sequence;
       public          postgres    false            �            1259    25634    evaluasi    TABLE     �   CREATE TABLE public.evaluasi (
    ideva bigint NOT NULL,
    hasilevaluasi character varying(255),
    kodeevaluasi character varying(255) NOT NULL,
    perluditingkatkan text,
    tanggalevaluasi date,
    nik bigint NOT NULL
);
    DROP TABLE public.evaluasi;
       public         heap    postgres    false            �            1259    25668    evaluasi_sequence    SEQUENCE     z   CREATE SEQUENCE public.evaluasi_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.evaluasi_sequence;
       public          postgres    false            �            1259    25429    himkriteria_sequence    SEQUENCE     }   CREATE SEQUENCE public.himkriteria_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.himkriteria_sequence;
       public          postgres    false            �            1259    25458    himpunan_kriteria    TABLE     �   CREATE TABLE public.himpunan_kriteria (
    nilai integer,
    idhim bigint NOT NULL,
    keterangan character varying(255),
    nmhimpunan character varying(255),
    nmkriteria character varying(255)
);
 %   DROP TABLE public.himpunan_kriteria;
       public         heap    postgres    false            �            1259    25641    jawaban    TABLE     z   CREATE TABLE public.jawaban (
    idja bigint NOT NULL,
    bobot integer,
    jawaban text,
    idper bigint NOT NULL
);
    DROP TABLE public.jawaban;
       public         heap    postgres    false            �            1259    25669    jawaban_sequence    SEQUENCE     y   CREATE SEQUENCE public.jawaban_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.jawaban_sequence;
       public          postgres    false            �            1259    25472    karyawan    TABLE     [  CREATE TABLE public.karyawan (
    tanggalmasuk date,
    idkar bigint NOT NULL,
    divisi character varying(255),
    email character varying(255) NOT NULL,
    jabatan character varying(255),
    masakerja character varying(255),
    nama character varying(255),
    nik character varying(255) NOT NULL,
    tingkatan character varying(255)
);
    DROP TABLE public.karyawan;
       public         heap    postgres    false            �            1259    25431    karyawan_sequence    SEQUENCE     z   CREATE SEQUENCE public.karyawan_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.karyawan_sequence;
       public          postgres    false            �            1259    25648 
   pertanyaan    TABLE     �   CREATE TABLE public.pertanyaan (
    idper bigint NOT NULL,
    jabatan character varying(255),
    kodepertanyaan character varying(255),
    pertanyaan text,
    koderule character varying(255)
);
    DROP TABLE public.pertanyaan;
       public         heap    postgres    false            �            1259    25670    pertanyaan_sequence    SEQUENCE     |   CREATE SEQUENCE public.pertanyaan_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.pertanyaan_sequence;
       public          postgres    false            �            1259    25492    picos    TABLE     P  CREATE TABLE public.picos (
    crosssellratio double precision NOT NULL,
    lowtouchratio double precision NOT NULL,
    pipelinestrength double precision NOT NULL,
    premiumcontribution double precision NOT NULL,
    tahun integer NOT NULL,
    idpicos bigint NOT NULL,
    nik bigint NOT NULL,
    bulan character varying(255)
);
    DROP TABLE public.picos;
       public         heap    postgres    false            �            1259    25433    picos_sequence    SEQUENCE     w   CREATE SEQUENCE public.picos_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.picos_sequence;
       public          postgres    false            �            1259    25655    rule    TABLE     �   CREATE TABLE public.rule (
    idrule bigint NOT NULL,
    jabatan character varying(255),
    koderule character varying(255) NOT NULL,
    rule character varying(255)
);
    DROP TABLE public.rule;
       public         heap    postgres    false            �            1259    25671    rule_sequence    SEQUENCE     v   CREATE SEQUENCE public.rule_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.rule_sequence;
       public          postgres    false            �            1259    25515    sales    TABLE     1  CREATE TABLE public.sales (
    jumlahcustomer integer NOT NULL,
    jumlahvisit double precision NOT NULL,
    tahun integer NOT NULL,
    targetgadus integer NOT NULL,
    targetpremium integer NOT NULL,
    targettotal integer NOT NULL,
    tercapaigadus integer NOT NULL,
    tercapaipersengadus double precision NOT NULL,
    tercapaipersenpremium double precision NOT NULL,
    tercapaipersentotal double precision NOT NULL,
    tercapaipremium integer NOT NULL,
    tercapaitotal integer NOT NULL,
    idsales bigint NOT NULL,
    nik bigint NOT NULL
);
    DROP TABLE public.sales;
       public         heap    postgres    false            �            1259    32915    sales_detail    TABLE     '  CREATE TABLE public.sales_detail (
    id bigint NOT NULL,
    bulan character varying(255),
    jumlahvisit double precision NOT NULL,
    targetblngadus integer NOT NULL,
    targetblnpremium integer NOT NULL,
    targetblntotal integer NOT NULL,
    tercapaiigadus integer NOT NULL,
    tercapaiipremium integer NOT NULL,
    tercapaiitotal integer NOT NULL,
    tercapaipersenngadus double precision NOT NULL,
    tercapaipersennpremium double precision NOT NULL,
    tercapaipersenntotal double precision NOT NULL,
    idsales bigint NOT NULL
);
     DROP TABLE public.sales_detail;
       public         heap    postgres    false            �            1259    25436    sales_sequence    SEQUENCE     w   CREATE SEQUENCE public.sales_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.sales_sequence;
       public          postgres    false            �            1259    32920    salesdetail_sequence    SEQUENCE     }   CREATE SEQUENCE public.salesdetail_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.salesdetail_sequence;
       public          postgres    false            �            1259    25438    userr_sequence    SEQUENCE     w   CREATE SEQUENCE public.userr_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.userr_sequence;
       public          postgres    false            �            1259    25527    users    TABLE        CREATE TABLE public.users (
    status boolean,
    created timestamp(6) with time zone,
    iduser bigint NOT NULL,
    nik bigint NOT NULL,
    kodeuser character varying(255),
    password character varying(255),
    role character varying(255),
    username character varying(255)
);
    DROP TABLE public.users;
       public         heap    postgres    false                      0    25439    bobot_kriteria 
   TABLE DATA           D   COPY public.bobot_kriteria (bobot, idbobot, nmkriteria) FROM stdin;
    public          postgres    false    222   3^                 0    25444    cpt 
   TABLE DATA           {   COPY public.cpt (coverage, coveragepersen, hitrate, panolcustomer, penetration, tahun, throughput, idcpt, nik) FROM stdin;
    public          postgres    false    223   �^                 0    25634    evaluasi 
   TABLE DATA           o   COPY public.evaluasi (ideva, hasilevaluasi, kodeevaluasi, perluditingkatkan, tanggalevaluasi, nik) FROM stdin;
    public          postgres    false    229   �^                 0    25458    himpunan_kriteria 
   TABLE DATA           ]   COPY public.himpunan_kriteria (nilai, idhim, keterangan, nmhimpunan, nmkriteria) FROM stdin;
    public          postgres    false    224   _                 0    25641    jawaban 
   TABLE DATA           >   COPY public.jawaban (idja, bobot, jawaban, idper) FROM stdin;
    public          postgres    false    230   �`                 0    25472    karyawan 
   TABLE DATA           p   COPY public.karyawan (tanggalmasuk, idkar, divisi, email, jabatan, masakerja, nama, nik, tingkatan) FROM stdin;
    public          postgres    false    225   �`                 0    25648 
   pertanyaan 
   TABLE DATA           Z   COPY public.pertanyaan (idper, jabatan, kodepertanyaan, pertanyaan, koderule) FROM stdin;
    public          postgres    false    231   Kc                 0    25492    picos 
   TABLE DATA           �   COPY public.picos (crosssellratio, lowtouchratio, pipelinestrength, premiumcontribution, tahun, idpicos, nik, bulan) FROM stdin;
    public          postgres    false    226   hc                 0    25655    rule 
   TABLE DATA           ?   COPY public.rule (idrule, jabatan, koderule, rule) FROM stdin;
    public          postgres    false    232   �c                 0    25515    sales 
   TABLE DATA           �   COPY public.sales (jumlahcustomer, jumlahvisit, tahun, targetgadus, targetpremium, targettotal, tercapaigadus, tercapaipersengadus, tercapaipersenpremium, tercapaipersentotal, tercapaipremium, tercapaitotal, idsales, nik) FROM stdin;
    public          postgres    false    227   �c                  0    32915    sales_detail 
   TABLE DATA           �   COPY public.sales_detail (id, bulan, jumlahvisit, targetblngadus, targetblnpremium, targetblntotal, tercapaiigadus, tercapaiipremium, tercapaiitotal, tercapaipersenngadus, tercapaipersennpremium, tercapaipersenntotal, idsales) FROM stdin;
    public          postgres    false    237   7i                 0    25527    users 
   TABLE DATA           a   COPY public.users (status, created, iduser, nik, kodeuser, password, role, username) FROM stdin;
    public          postgres    false    228   �t       (           0    0    bobotkriteria_sequence    SEQUENCE SET     D   SELECT pg_catalog.setval('public.bobotkriteria_sequence', 5, true);
          public          postgres    false    215            )           0    0    cpt_sequence    SEQUENCE SET     :   SELECT pg_catalog.setval('public.cpt_sequence', 2, true);
          public          postgres    false    216            *           0    0    evaluasi_sequence    SEQUENCE SET     @   SELECT pg_catalog.setval('public.evaluasi_sequence', 1, false);
          public          postgres    false    233            +           0    0    himkriteria_sequence    SEQUENCE SET     C   SELECT pg_catalog.setval('public.himkriteria_sequence', 35, true);
          public          postgres    false    217            ,           0    0    jawaban_sequence    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.jawaban_sequence', 1, false);
          public          postgres    false    234            -           0    0    karyawan_sequence    SEQUENCE SET     @   SELECT pg_catalog.setval('public.karyawan_sequence', 28, true);
          public          postgres    false    218            .           0    0    pertanyaan_sequence    SEQUENCE SET     B   SELECT pg_catalog.setval('public.pertanyaan_sequence', 1, false);
          public          postgres    false    235            /           0    0    picos_sequence    SEQUENCE SET     <   SELECT pg_catalog.setval('public.picos_sequence', 2, true);
          public          postgres    false    219            0           0    0    rule_sequence    SEQUENCE SET     <   SELECT pg_catalog.setval('public.rule_sequence', 1, false);
          public          postgres    false    236            1           0    0    sales_sequence    SEQUENCE SET     =   SELECT pg_catalog.setval('public.sales_sequence', 73, true);
          public          postgres    false    220            2           0    0    salesdetail_sequence    SEQUENCE SET     D   SELECT pg_catalog.setval('public.salesdetail_sequence', 134, true);
          public          postgres    false    238            3           0    0    userr_sequence    SEQUENCE SET     <   SELECT pg_catalog.setval('public.userr_sequence', 2, true);
          public          postgres    false    221            R           2606    25443 "   bobot_kriteria bobot_kriteria_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY public.bobot_kriteria
    ADD CONSTRAINT bobot_kriteria_pkey PRIMARY KEY (idbobot);
 L   ALTER TABLE ONLY public.bobot_kriteria DROP CONSTRAINT bobot_kriteria_pkey;
       public            postgres    false    222            T           2606    25448    cpt cpt_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY public.cpt
    ADD CONSTRAINT cpt_pkey PRIMARY KEY (idcpt);
 6   ALTER TABLE ONLY public.cpt DROP CONSTRAINT cpt_pkey;
       public            postgres    false    223            d           2606    25640    evaluasi evaluasi_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.evaluasi
    ADD CONSTRAINT evaluasi_pkey PRIMARY KEY (ideva);
 @   ALTER TABLE ONLY public.evaluasi DROP CONSTRAINT evaluasi_pkey;
       public            postgres    false    229            V           2606    25464 (   himpunan_kriteria himpunan_kriteria_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.himpunan_kriteria
    ADD CONSTRAINT himpunan_kriteria_pkey PRIMARY KEY (idhim);
 R   ALTER TABLE ONLY public.himpunan_kriteria DROP CONSTRAINT himpunan_kriteria_pkey;
       public            postgres    false    224            h           2606    25647    jawaban jawaban_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.jawaban
    ADD CONSTRAINT jawaban_pkey PRIMARY KEY (idja);
 >   ALTER TABLE ONLY public.jawaban DROP CONSTRAINT jawaban_pkey;
       public            postgres    false    230            X           2606    25480    karyawan karyawan_email_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.karyawan
    ADD CONSTRAINT karyawan_email_key UNIQUE (email);
 E   ALTER TABLE ONLY public.karyawan DROP CONSTRAINT karyawan_email_key;
       public            postgres    false    225            Z           2606    25482    karyawan karyawan_nik_key 
   CONSTRAINT     S   ALTER TABLE ONLY public.karyawan
    ADD CONSTRAINT karyawan_nik_key UNIQUE (nik);
 C   ALTER TABLE ONLY public.karyawan DROP CONSTRAINT karyawan_nik_key;
       public            postgres    false    225            \           2606    25478    karyawan karyawan_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.karyawan
    ADD CONSTRAINT karyawan_pkey PRIMARY KEY (idkar);
 @   ALTER TABLE ONLY public.karyawan DROP CONSTRAINT karyawan_pkey;
       public            postgres    false    225            j           2606    25654    pertanyaan pertanyaan_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY public.pertanyaan
    ADD CONSTRAINT pertanyaan_pkey PRIMARY KEY (idper);
 D   ALTER TABLE ONLY public.pertanyaan DROP CONSTRAINT pertanyaan_pkey;
       public            postgres    false    231            ^           2606    25496    picos picos_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.picos
    ADD CONSTRAINT picos_pkey PRIMARY KEY (idpicos);
 :   ALTER TABLE ONLY public.picos DROP CONSTRAINT picos_pkey;
       public            postgres    false    226            n           2606    25661    rule rule_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.rule
    ADD CONSTRAINT rule_pkey PRIMARY KEY (idrule);
 8   ALTER TABLE ONLY public.rule DROP CONSTRAINT rule_pkey;
       public            postgres    false    232            r           2606    32919    sales_detail sales_detail_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.sales_detail
    ADD CONSTRAINT sales_detail_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.sales_detail DROP CONSTRAINT sales_detail_pkey;
       public            postgres    false    237            `           2606    25519    sales sales_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.sales
    ADD CONSTRAINT sales_pkey PRIMARY KEY (idsales);
 :   ALTER TABLE ONLY public.sales DROP CONSTRAINT sales_pkey;
       public            postgres    false    227            l           2606    25665 '   pertanyaan uk_46vsso9r12x6bltk1q7mk8404 
   CONSTRAINT     f   ALTER TABLE ONLY public.pertanyaan
    ADD CONSTRAINT uk_46vsso9r12x6bltk1q7mk8404 UNIQUE (koderule);
 Q   ALTER TABLE ONLY public.pertanyaan DROP CONSTRAINT uk_46vsso9r12x6bltk1q7mk8404;
       public            postgres    false    231            p           2606    25667 !   rule uk_k0f5thn0e4pe1v2lk7pr1yjen 
   CONSTRAINT     `   ALTER TABLE ONLY public.rule
    ADD CONSTRAINT uk_k0f5thn0e4pe1v2lk7pr1yjen UNIQUE (koderule);
 K   ALTER TABLE ONLY public.rule DROP CONSTRAINT uk_k0f5thn0e4pe1v2lk7pr1yjen;
       public            postgres    false    232            f           2606    25663 %   evaluasi uk_k39gvjvandbwxe9ovmig5irsx 
   CONSTRAINT     h   ALTER TABLE ONLY public.evaluasi
    ADD CONSTRAINT uk_k39gvjvandbwxe9ovmig5irsx UNIQUE (kodeevaluasi);
 O   ALTER TABLE ONLY public.evaluasi DROP CONSTRAINT uk_k39gvjvandbwxe9ovmig5irsx;
       public            postgres    false    229            b           2606    25533    users users_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (iduser);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    228            s           2606    25534    cpt fkac3lnpbkopnatnxukbndv42is    FK CONSTRAINT     �   ALTER TABLE ONLY public.cpt
    ADD CONSTRAINT fkac3lnpbkopnatnxukbndv42is FOREIGN KEY (nik) REFERENCES public.karyawan(idkar);
 I   ALTER TABLE ONLY public.cpt DROP CONSTRAINT fkac3lnpbkopnatnxukbndv42is;
       public          postgres    false    225    4700    223            w           2606    25672 $   evaluasi fkaue0annwrs4cvk01wu5otatrk    FK CONSTRAINT     �   ALTER TABLE ONLY public.evaluasi
    ADD CONSTRAINT fkaue0annwrs4cvk01wu5otatrk FOREIGN KEY (nik) REFERENCES public.karyawan(idkar);
 N   ALTER TABLE ONLY public.evaluasi DROP CONSTRAINT fkaue0annwrs4cvk01wu5otatrk;
       public          postgres    false    229    225    4700            t           2606    25554 !   picos fkb1yk5v61am163rely0j5jptf4    FK CONSTRAINT     �   ALTER TABLE ONLY public.picos
    ADD CONSTRAINT fkb1yk5v61am163rely0j5jptf4 FOREIGN KEY (nik) REFERENCES public.karyawan(idkar);
 K   ALTER TABLE ONLY public.picos DROP CONSTRAINT fkb1yk5v61am163rely0j5jptf4;
       public          postgres    false    225    4700    226            u           2606    25564 !   sales fkesqhtkvdm2gowj1db6n3nu9m9    FK CONSTRAINT     �   ALTER TABLE ONLY public.sales
    ADD CONSTRAINT fkesqhtkvdm2gowj1db6n3nu9m9 FOREIGN KEY (nik) REFERENCES public.karyawan(idkar);
 K   ALTER TABLE ONLY public.sales DROP CONSTRAINT fkesqhtkvdm2gowj1db6n3nu9m9;
       public          postgres    false    227    4700    225            z           2606    32921 (   sales_detail fkgitunt55as479261j2yttt58p    FK CONSTRAINT     �   ALTER TABLE ONLY public.sales_detail
    ADD CONSTRAINT fkgitunt55as479261j2yttt58p FOREIGN KEY (idsales) REFERENCES public.sales(idsales);
 R   ALTER TABLE ONLY public.sales_detail DROP CONSTRAINT fkgitunt55as479261j2yttt58p;
       public          postgres    false    4704    227    237            y           2606    25682 &   pertanyaan fkkgqea25gby0k2hesqyu1dj4kh    FK CONSTRAINT     �   ALTER TABLE ONLY public.pertanyaan
    ADD CONSTRAINT fkkgqea25gby0k2hesqyu1dj4kh FOREIGN KEY (koderule) REFERENCES public.rule(koderule);
 P   ALTER TABLE ONLY public.pertanyaan DROP CONSTRAINT fkkgqea25gby0k2hesqyu1dj4kh;
       public          postgres    false    4720    232    231            x           2606    25677 #   jawaban fkqyypygv5cn16olsvqml5meq2p    FK CONSTRAINT     �   ALTER TABLE ONLY public.jawaban
    ADD CONSTRAINT fkqyypygv5cn16olsvqml5meq2p FOREIGN KEY (idper) REFERENCES public.pertanyaan(idper);
 M   ALTER TABLE ONLY public.jawaban DROP CONSTRAINT fkqyypygv5cn16olsvqml5meq2p;
       public          postgres    false    4714    231    230            v           2606    25574 !   users fkrcx0xbhv9jak6vgeb8a9bcqjt    FK CONSTRAINT     �   ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkrcx0xbhv9jak6vgeb8a9bcqjt FOREIGN KEY (nik) REFERENCES public.karyawan(idkar);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT fkrcx0xbhv9jak6vgeb8a9bcqjt;
       public          postgres    false    228    225    4700               \   x�3�4�tL��,K�M�+QpOL)-�2�4F(J��,��21�4D�/I��22�4��*��I�P�,�,�26�4��8����q��qqq `�!m         ;   x�-ʱ�0��� E%�0��(��?�%A$\Ф�4�^�q�y�w�g�="Q�WD|�
�            x������ � �         v  x��Աn�0������+���M�J�R�Q�.�A"$�}�N1&�
��؆��<}�}���m��J���P�Tmu��ݹ/��\2�s�T��L�g�]�ֶLW^�l�cy���ҟ۪��K%�꽾�}$��xO�HuX.)� �V�%巀��T��Ą��8��D�"Q3��}�ě�n(5B����9Sz1Ϙ��2�ΡaQ��Pڋc��)>��1m�Y .�\0�8\~߹ O�O2"%\�
��4N��4����&cD��!'�	�$,�P��k�I'�r��a.�v)<<�5$���)23�X�	�M�E��	�� ��	�[qFt)���d��IVZ�� �̀���AS�r>�� �Z�7��CE���x�            x������ � �         �  x�}��n�0Ư�S����]A�j�UP6M�͡q���ќX]�~�vh�'��ٿ|>�΁R�.܇0��=�6����u��:7��k]�����kɝ<�iY%�lZm�G���춫�{|p�/H�P�c�)�_P~AS��GЅh�:��/)��&e�}A)�R�mW��r�۷BI����@P��1�Ւ��P�2�g!_M �8	��M����,2���A׹7���K��S˝�'�R4Q,jɳȲ���tjy�������b6�� ��	�
:Э�.'����zP3�2�Po���	]�;(���"���+�H�A�u�z���O<���d4 Z�
��:�{�VY$O}-?�s�L	c�4Ę߬�܊R�I~�}���rӀ���\��x��d��o���Y�wv��hY�M4��'�n�e����h�3l�M�T�m��������r�`ai�u����P�<����{]����'t6�O������bY�:��ؔ��?L�wYࠋ0��,�3V��c��8��D`��d�֟&��'+%6�m_��x����
�	��=L,��Bw�X�ط���P~y�����ŕ��w/��<�LzV8Sq����`F[��a��pVu�����(�Ut���g���Q8��5�!uq�B�y)^.G��_d	4�            x������ � �         A   x�3Գ�47�4�FzFƜF@Ә�%�857)���Pτ�܈�D�����(�P���� $)            x������ � �         Q  x�]�ۑ�8D��`X@<�č`6�$���g���,kd�F��'��]����n2��j,�|���%�����u��k�y?gHmN�9qʚ���\9���ƒ�Gvrvn-Oy_�e�w�9�u����6����0����G��Tc����J�74�`��q�:�4}�r|���2�e�ψ�}d�з�X�~TL�#�Yv!���)���^$�|���P=D)�ul�E 5&O�<\ 0����z@�b�;��ϡ,V��	���]-bz"� ��!��Q�ְ����q(�N��&��R�]0��1o��
^�2��/7�;s����5kؚ�W�I�f�����2���ò�e9|����|3FB� �ĴER�ʹ��^�m����YM�]�&�7)����Z�wRb
������G��NE��;β�7M�K��&�E%�����;-=�/���=����ꐯ�bT-ratq#X䅇�A����v�H ۿ��E)ֆ��%^p64��PAo#�S*)<Z�m.u�E�J�-��ۢ�T�i�]/�RȊ;Q�LR���~�qO�T�3��ʩ�ȡ���F���_ܽ�k�+h����7lXU��@6h)(wH��sD�g���"e��`���_��l���������I�Qp�KOk�|��2�=��:��J>�)��A���^�;#zІ���aQ(K�?��3���6��wv5�:P�����'����Z��Eh�5��l���O)�-��I�:@�'ܤ�����V����@a�g �AcTkL4�is�hIF��D �Z�U���	펫U�B��~�C�J�?tRM)[�L�hfT-×��C�__r�Z)��t~�_�� ؁m���يѥŠP`#�3�
 ��[�DUB�n':8{\�܅u;I���Q#�(�1,`'��P24���'�ݍI�ѷ��:�k���.t�i��gq��`=G��L?����t<Rw5
��O��"5w�E��� nJ� `�k�=-P���(���U�J��?�u�����e,�;��*v������g)o1�}�@$�1�"�5���|2Ш��K�;d�?�wV#�<�pZ5��7r�b�x׸��I�a�`C�x�]~��&�a�j��kC�s�G�=��Ha��6�#��������0�W�'�>�f��0�?gb�;�wt,���p�/z�}�T}���k>������L��B�����ϳ���&^H.%�+���|���,Z�a�$һ�Q.)� U�wMڗD�M����~-W�+���`��	����T`���0{�YֱX����\<���4��ߴ3Ja��+��n¶ ������u��          @  x�mYˎ]7\��1�(Q���f��� � @�������ۍ��*��X����˟߿|�����яZ��=���0�k���X����#�y����__��M`�X=*�v4�r�[G��cm	vK-��o_�}���Z��!��M�q�<~�߷��8���[��|�H��嚦d�����0Y�������RT������?\�N]'��v�Q#U?��V* ���u������#��\�a��������_op�� @��5�b<�]8���Ee�c�<��C�v�p:�S���Va,�k�ȕa�`Xϴc� �hg�G����G���Ό%W+�[���[�qO}��#�ut?������rV��ֹ6x$<��	:����^�+��7�s�?I�C{jC�y��������<�QS闳<	���y�:�T51g>��UQ?f=uً��ѝ�s[Uf`_�ycSG ]�rW%V*���ڙ�py�-��i(Y&�%WT�]�+\>b���(H��qA<�@���d�NȜ��EFN�=�����4�9�}�ni��ŘC�C1�޸�^���?Փ!S_��?��K�ͽX[�о]�)�֏��
��o�<���)Xopʎ�y�g_�1�b�e�P8�	�R|�el��GUx%<�`y 6��&pD����	&B�0��P��qr'�!)�	\�@�le`}Bz�fVݜ�"�S]�ֳ���br્6�'�)%�P�`C.�*�_E$��P�����X�eZ��n"�!ˈlw�!o�G��B�KF���d�#��K2��ؕ��������*gD�N�����)���
�x�
Xe�E�oIYG*:[��Ƴ-3ۍ_�`��:hw�|�ڑ�	��ʱ���E�]KfG,lBh_Xe�eQy��;G'�`�����ј�)��(�������~
�c(��l��:Y
�g~i���{ME��|�L\�"r!d�['G"1f�B�7�E^I�	&�L��3O��+��X��З@LU!h,H�b.
4�Ș�#-���LuW�!�ux�݃]�n�f�T�}oO�5�/ys�IW(��{bc���q�},B@7[I �#�T��<ŋ��}�!�p�`����*r��Ȫ���'4��zeC'�)�"H:\T�)3�o� �U��������<b�W�mH#|�.�Հc%fk �����W����Y�؉��B;gN1z�@��uC6_�rQQ�"![D	=�e�o��C;/�CQ�*'��v?*c,�L>n�\���c�����i�\���(p���[0u�2<�����O�O{�"x���v�iݩ�ȗ�$(��ӚS�dŌk�Ok��Y��唟w���I��L�?m�����*_}��j.��	{)N�ɺ��=˫U!�Q{H�\B9�t4�I7hܿ~�'��0�N��ƴ�K�f�uZ�{�,j��j���1��M˛|��ڟ���uY(�%?�'��'b���ۚ��?���;^�~ ��[�����Ҟ%^@�N�O�xv$�h�W�&(}�9<�u��Ìu���iL�26uV��p��{E�j���	>��� ��)����6�J;A�\I��u\]�1���N�w�P�z�~��DP=��hS���16G�d[��e��Ƞe��b�|�����hND��<pu��F�hL�$����f�$�[�g|Ɗ+t��0�U^Am����K�o�f[vf_B�!!qH� ��0Ŭ:��ɻ ���0���IE���wu;ޔ%��1k�������i��q.-sO����D���8�~������kWx�mml/�̧_����Ȳns���i��t]��^{zߢ�6�\{���k��̅uw�X�AE�@��<WE��!�$`U:�}�?c��3`p�`#�}*>Eķ�85Θ���{�	��	�Ѝ����|�b!�H��Up@2���j��Mn�6Rc���V�$f�o�\��J�8G2(�j�&��ɛ��K�m�u�X6���b/�#�{v*u"�9㓑�Rw�)(�
��D����b?�
Cb/#)s����h/r2���4�-�A������0��jJ|r�Ue:>��gl�����mY/5>t!�#!m)�\оCۉ��sj��9�^�tHhb�T��K��aWk�&Y��<#f�*�A3BV-n���o]\��Ǉq?��p�d��lK�S�$�f�a����!,���Ы+���?qAy��X��/�	��ʟb&d"@]�%w� ���T2������LJ^�S"2����.*�~�6pn�����1`�E��0:W> �G����N7!�@����T0g��$<�0Gm����L"5�xhe�����/ �a����#Hd���Y�&�Ug�[�P�X����t��6�4��;氼�|�l�G(.�Ɯ�R�]���n��ᖟ-�������;魘�^���I��7�~�z5�s-(����E\&�����w����C]Kq����	��Р�z����k���B:��sk���Hej���>ES�H�PH�!f0Ϥ�n�&?~�`�<idN�3
����"��;3����W����6�롳��P�R�UX��5�����f�w�ɉ�����gckd�.1	D��i{	�*g��}� t[�zΦ���� ~V�]�������Qp|2
�,����j
��if{�:D�Kp$�|�������@yO�'�U�x��s4a�3�aṫh�^���b.�U�Ȿ���ʛ��V}������֤��0��#�Yj�t�ԋ������5�[�^��C�S�*�s�n�՘�}YSo��9=��e����8��m��)�KHJ	�؇��ū�1�yx��4�I������c�         �   x�m�;�0 g�+���b1n���/h\l�����׋1���r��E!@����tB1ƴ��+b6u#�;�ǊJ�*&�]p'u��ܑ��{����I��#�%W��A�4�ٰ���u��������=',v��vS�Czl��d���A��9���ૐ��{��ʹ<���(�v�SH�8yZ����A�     