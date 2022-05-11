# This program prompts the user for the index of the Fibonacci number they'd like to see
# and prints the corresponding Fibonacci number. The sequence starts with 0 as the 
# first Fibonacci number and 1 as the second.
#
# @author Angela Jia
# @version 5/11/22

.data 
msg: 
.asciiz "Enter the index of the Fibonacci number you'd like to see:\n"
msg2:
.asciiz "Invalid index.\n"
.text 0x00400000
.globl main
main:
	la $a0, msg
	li $v0, 4
	syscall			# print msg
	li $v0, 5
	syscall		
	ble $v0, 0, invalid
	move $t0, $v0		# read n into $t0 
	li $t1, 0		# store odd-indexed fib num
	li $t2, 1		# store even-indexed fib num
	beq $t0, 1, basezero 
	beq $t0, 2, baseone
	subu $t0, $t0, 2
	addu $t1, $t2, $t1
	move $a0, $t1
	subu $t0, $t0, 1	# make sure $t0, $t1 have nonzero values
	li $t4, 2		# set 2 in $t4 for division
	j loop
	
basezero:
	move $a0, $t1
	j end
	
baseone:
	move $a0, $t2
	j end
	
loop:
	beq $t0, 0, end
	divu $t0, $t4
	mfhi $t3	# $t3 stores whether n is even or odd
	beq $t3, 0, add0
	addu $t1, $t1, $t2
	move $a0, $t1
	subu $t0, $t0, 1
	j loop
	
add0:
	addu $t2, $t2, $t1
	move $a0, $t2
	subu $t0, $t0, 1
	j loop

end:
	li $v0, 1
	syscall		# print nth fib num
	li $v0, 10
	syscall		# terminate
	
invalid:
	la $a0, msg2
	li $v0, 4
	syscall		# print invalid message
	j main
	
	