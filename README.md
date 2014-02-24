MyExcel
=======

Calculate excel tables file (**.csv**) from **in** folder and put it's in **out** folder
(which created if not exists)

.csv file must be in **UTF-8 without BOM** encoding


Example input .csv file:

```bash
2.3::=1,23+2.3*(5+7.3):2:3:
=A1*2.5+C1:=5/0:=2+A1:=E2:=D2
::::
1.2:1,3:=D1+A4
```

Example output .csv file:

```bash
2.3::29.52:2:3:
35.269999999999996:#:4.3:#:#:

1.2:1,3:3.2:
```

Console:

```bash
E:\JavaProjects\MyExcel>java -jar myexcel.jar
Enter command :
    go   - for bagining calculation
    exit - for exit
go
file: in\table1.csv
done
file: in\test.csv
done
exit
```