USE ecoexp;

DROP PROCEDURE IF EXISTS word_freq;
DELIMITER $$
CREATE PROCEDURE word_freq(keyword TEXT)
BEGIN
DECLARE total INT;
DECLARE len INT;
DECLARE i INT;
DECLARE str TEXT;
DECLARE cur CURSOR FOR SELECT detail FROM programs;

SET total=0;
SET str='';
SET len=0;
SET i=0;

SELECT COUNT(*) INTO len FROM programs; 
open cur;
REPEAT
FETCH cur INTO str;
REPEAT
select locate(keyword,str,i+1) into i;
if i > 0 then set total=total+1;
end if;
UNTIL i=0 END REPEAT;
SET len= len-1;
UNTIL len=0 END REPEAT;
CLOSE cur;

select total;
END$$

DELIMITER ;
