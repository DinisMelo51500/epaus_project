/*
 * ISEL-DEI-SisInf
 * ND 2022-2026
 *
 *   
 * Information Systems Project - Active Databases
 * Didactic material to support 
 * the Information Systems course
 * 
 *  * */

/* ### DO NOT CHANGE OR REMOVE THE MARKERS BELOW 
 * ### ONLY WRITE to THE TODO ZONE
 * ### */


-- region Question 1.a 
CREATE OR REPLACE FUNCTION fn_validar_nif()
RETURNS TRIGGER
LANGUAGE plpgsql
AS
$$
DECLARE
    soma INTEGER := 0;
    i INTEGER;
    digito_controle INTEGER;
BEGIN
    NEW.nif := TRIM(NEW.nif);

    IF NEW.nif !~ '^[0-9]{9}$' THEN
        RAISE EXCEPTION 'NIF inválido';
    END IF;

    IF SUBSTRING(NEW.nif,1,1) NOT IN ('1','2','3','5','6','8','9') THEN
        RAISE EXCEPTION 'Prefixo inválido';
    END IF;

    FOR i IN 1..8 LOOP
        soma := soma +
            CAST(SUBSTRING(NEW.nif,i,1) AS INTEGER) * (10 - i);
    END LOOP;

    digito_controle := 11 - (soma % 11);

    IF digito_controle >= 10 THEN
        digito_controle := 0;
    END IF;

    IF digito_controle <>
       CAST(SUBSTRING(NEW.nif,9,1) AS INTEGER) THEN

        RAISE EXCEPTION 'NIF inválido';
    END IF;

    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER trg_validar_nif
BEFORE INSERT OR UPDATE OF nif
ON cliente
FOR EACH ROW
EXECUTE FUNCTION fn_validar_nif();
-- endregion


-- region Question 1.b
--validar email
CREATE OR REPLACE FUNCTION fn_validar_email_duplicado()
RETURNS TRIGGER
LANGUAGE plpgsql
AS
$$
BEGIN

    IF EXISTS (
        SELECT 1
        FROM contacto_email ce
        WHERE ce.cliente_nif = NEW.cliente_nif
          AND LOWER(ce.email) = LOWER(NEW.email)
          AND ce.contacto_email_id <>
              COALESCE(NEW.contacto_email_id, -1)
    ) THEN

        RAISE EXCEPTION
            'Cliente já possui este email';
    END IF;

    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER trg_validar_email_duplicado
BEFORE INSERT OR UPDATE
ON contacto_email
FOR EACH ROW
EXECUTE FUNCTION fn_validar_email_duplicado();

--validar telefone
CREATE OR REPLACE FUNCTION fn_validar_telefone_duplicado()
RETURNS TRIGGER
LANGUAGE plpgsql
AS
$$
BEGIN

    IF EXISTS (
        SELECT 1
        FROM contacto_telefone ct
        WHERE ct.cliente_nif = NEW.cliente_nif
          AND ct.telefone = NEW.telefone
          AND ct.contacto_telefone_id <>
              COALESCE(NEW.contacto_telefone_id, -1)
    ) THEN

        RAISE EXCEPTION
            'Cliente já possui este telefone';
    END IF;

    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER trg_validar_telefone_duplicado
BEFORE INSERT OR UPDATE
ON contacto_telefone
FOR EACH ROW
EXECUTE FUNCTION fn_validar_telefone_duplicado();
-- endregion

-- region Question 2
CREATE OR REPLACE FUNCTION fx_media_movel(days integer, instrumento_isin VARCHAR(12)) 
RETURNS TABLE (isin varchar, media numeric(15,2)) AS
$$
declare
	dia date;
begin
	dia := current_date - days;
	RETURN QUERY
    select instrumento_isin, avg(valor)::numeric(15,2)
    from triplo_externo
    where data_tempo >= dia and identificador = instrumento_isin;
end;
$$ language plpgsql;

--select * from fx_media_movel(30, 'PTGAL0AM0009');
-- endregion

-- region Question 3
CREATE OR REPLACE FUNCTION fx_portefolio_info(portfolio_id int)
RETURNS table(portefolio bigint, instrumento_isin varchar(12), quantidade numeric(15,4), valor_atual numeric(15,2), percentagem_variacao_diaria numeric(7,2)) as
$$
begin
	return QUERY
	select vp.portefolio, vp.instrumento_isin, vp.quantidade, df.valor_actual, df.percentagem_variacao_diaria 
	from valor_posicao vp join dados_fundamentais df on vp.instrumento_isin = df.instrumento_isin 
	where vp.portefolio = portfolio_id;
	
end;
$$ language plpgsql;

--select * from fx_portefolio_info(2);
-- endregion
 
-- region Question 4
CREATE OR REPLACE PROCEDURE p_actualizaValorDiario(identificador varchar(12), datatempo timestamp, valor numeric(15,2))
as $$
declare
	v_data DATE;
begin
	v_data := DATE(datatempo);

	if exists(
		select 1 from valor_instrumento_diario where instrumento_isin = identificador
		and data = v_data
	) then
		
		update valor_instrumento_diario
		set valor_minimo = LEAST(valor_minimo, valor), valor_maximo = GREATEST(valor_maximo, valor), valor_fecho = valor
		where instrumento_isin = identificador and data = v_data;
	else
		insert into valor_instrumento_diario(instrumento_isin, data, valor_minimo, valor_maximo, valor_abertura, valor_fecho)
		values (identificador, v_data, valor, valor, valor, valor);
	end if;
end;
$$ language plpgsql;

--call p_actualizaValorDiario('PTGAL0AM0009','2026-04-24 00:00:00',14.7);
-- endregion

-- region Question 5
CREATE OR REPLACE VIEW contacto_cliente(nif,carta_cidadao,nome,tipo_contacto,contacto,descricao)
as 
select nif, cartao_cidadao as carta_cidadao, nome, 'email' as tipo_contacto, email as contacto, descricao
from cliente c join contacto_email ce on c.nif = ce.cliente_nif
union all
select nif, cartao_cidadao as carta_cidadao, nome, 'telefone' as tipo_contacto, telefone as contacto, descricao
from cliente c join contacto_telefone ct on c.nif = ct.cliente_nif ;

create or replace trigger allow_insert_on_client_view
instead of insert on contacto_cliente
for each row 
execute function trg_insert_client_view();

create or replace function trg_insert_client_view()
returns trigger as $$
begin
insert into cliente(nif, cartao_cidadao, nome) values (new.nif, new.carta_cidadao, new.nome) on conflict do nothing;
if new.tipo_contacto = 'email' then
	insert into contacto_email(cliente_nif, descricao, email) values(new.nif, new.descricao, new.contacto);
elseif new.tipo_contacto = 'telefone' then
	insert into contacto_telefone(cliente_nif, descricao, telefone) values(new.nif, new.descricao, new.contacto);
end if;
return null;
end;
$$ language plpgsql;

--insert into contacto_cliente (nif, carta_cidadao, nome, tipo_contacto, contacto, descricao)
--values(181123123, '12345678 0 ZW3', 'John Doe', 'telefone', '+351912345678', 'Telemóvel');

--insert into contacto_cliente (nif, carta_cidadao, nome, tipo_contacto, contacto, descricao)
--values(181123123, '12345678 0 ZW3', 'John Doe', 'email', 'john.doe@email.com', 'Email pessoal');
-- endregion

-- region Other changes
--TODO
ALTER TABLE cliente
ADD COLUMN versao INTEGER NOT NULL DEFAULT 0;

ALTER TABLE contacto_email
ADD COLUMN versao INTEGER NOT NULL DEFAULT 0;

ALTER TABLE contacto_telefone
ADD COLUMN versao INTEGER NOT NULL DEFAULT 0;
-- alteração necessária para optimistic locking
-- endregion
