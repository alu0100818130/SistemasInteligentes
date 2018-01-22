#!/bin/bash
 
    convert -size 175x24 $1 +repage -crop 175x24+17+17 wewe.jpg #&> /dev/null 
    convert wewe.jpg -resize 1248x180 wewe2.jpg #&> /dev/null 
    rm -rf wewe.jpg
    ocropus-nlbin -n wewe2.jpg -o book #&> /dev/null	
	rm -rf wewe2.jpg 
    ocropus-gpageseg -n 'book/????.bin.png' #&> /dev/null 
    ocropus-rpred -m /mnt/c/Users/Perry/Desktop/Programa/ocropy/soloclear-00080000.pyrnn.gz 'book/????/??????.bin.png' -n #&> /dev/null

    cat book/0001/010001.txt > /mnt/c/Users/Perry/Desktop/Programa/ocropy/a.txt
    rm -rf ./book/*