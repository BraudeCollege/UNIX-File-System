This assignment contains two tasks.

Task 1 - Completing bitwise operations
This part is straigtforward, we use bitmasks[] to compare ith digit in input byte b.
The bitmasks[i] contains the integer whose binary representation has one only 1 in its ith digit.
The only tricky part is when the input is a byte array "byte bytes[]". The calculation here is very similar to find right block number and pointer in task2.


Task 2 - Completing getDirectBlock(int fd, MODE mode) function in MyFileSystem.java
This part involves freeMap.find() to find free datablock, and disk operations (read, write) to support constructing IndirectBlock.

From course slides we know -
A singly IndirectBlock's count is singlyCount = IndirectBlock.COUNT.
A doubly IndirectBlock may hold up to doublyCount = IndirectBlock.COUNT * IndirectBlock.COUNT count.
A triply IndirectBlock holds up to triplyCount = IndirectBlock.COUNT * IndirectBlock.COUNT * IndirectBlock.COUNT count.

Then the implementation of this function needs to be discussed by cases -
The default case is when blockNum <= 9, this case is already implemented.
Second case is when 9 < blockNum < 10 + singlyCount, in this case, we only need one singly IndirectBlock, which is connected by the iNode. So try construct this singly IndirectBlock, if fails, the whole function should return null.
Third case is when 10 + singlyCount <= blockNum < 10 + singlyCount + doublyCount. In this case, we need one Singly IndirectBlock and a Doubly IndirectBlock, so we construct these two IndirectBlock in sequence, return null if either step fails.
Fouth case is when 10 + singlyCount + doublyCount <= blockNum < 10 + singlyCount + doublyCount + triplyCount. In this case, we need one Singly IndirectBlock, one Doubly IndirectBlock, and one Triply IndirectBlock. Similarly, we construct these three in sequence, return null if any step fails.

To support the construction of IndirectBlock, we have 2 helper functions. constructIB1() and constructIB2(). The first one is to help constructing an IndirectBlock by inode (this is only used to construct singly IndirectBlock). The second is to consctucting an IndirectBlock from another IndirectBlock, this is used to construct doubly and triply IndirectBlock. These two helper functions have same content, except different parameters. They both check passed in pointer and freeMap, and use either disk.read() to read block into created IndirectBlock or use disk.write() to write IndirectBlock to disk, and finally return null (if disk is full) or the IndirectBlock.

Once get the IndirectBlock which needs to be pointed to DirectBlock. A helper function getDirectBlockByIB() is called. IndirectBlock, final blocknum, blockOff, and mode are passed in. In read mode: check if there is a hole, if yes return DirectBlock.hole, else return DirectBlock. In write mode: check if there is space to write (using freeMap), if yes return DirectBlock, else return null.