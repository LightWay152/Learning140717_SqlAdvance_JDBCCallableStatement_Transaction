Use EShopV10
GO

CREATE PROC spInsertCategoryWithoutOutputId
(
	@Name VARCHAR(50),
	@NameVN NVARCHAR(50)
)
AS
BEGIN
	INSERT INTO Categories(Name,NameVN)
			VALUES(@Name,@NameVN)
END
/*test proc spInsertCategoryWithoutOutputId without OUTPUT Id*/
EXEC spInsertCategoryWithoutOutputId 'Chair',N'Ghế đẩu'
SELECT * FROM Categories
GO

CREATE PROC spInsertCategoryWithOutputId
(
	@Name VARCHAR(50),
	@NameVN NVARCHAR(50),
	@Id INT OUTPUT
)
AS
BEGIN
	INSERT INTO Categories(Name,NameVN)
			VALUES(@Name,@NameVN)
	SET @Id=@@IDENTITY
END

/*test proc spInsertCategoryWithOutputId*/
EXEC spInsertCategoryWithOutputId 'Book',N'Sách',1062
SELECT * FROM Categories
GO

CREATE PROC spSearchProduct
(
	@Min FLOAT,
	@Max FLOAT
)
AS
BEGIN
	SELECT * FROM Products WHERE UnitPrice BETWEEN @Min AND @Max
END
GO

/*test proc spSearchProduct*/
EXEC spSearchProduct 5, 10
SELECT * FROM Categories
GO

