# CDK Stratus Resolver

## How To Use

### Entering Input

1. Copy and paste CDK parts list. Obtain this list by going into the service 
daily log (SDL) and clicking on the RO. Simply copy and paste all parts from
the list of parts on the appropriate line. Make sure not to include any lines
that are not parts lines. 

2. Next hit enter, type the '^' character, then the enter key again to indicate 
to the program you are done entering input for CDK parts.

3. Copy and paste the parts from Stratus. Do not include the headings or any 
lines text that are not parts lines. Include the entire line

4. To indicate you are done adding parts from Stratus: hit enter, type the '^'
character, then hit the enter key again to indicate you are done entering 
parts from Stratus. 

### Interpreting Output

Processed Stratus Parts Difference: 
The program will output all parts in common between both programs that had
either a different total price, different quantity (or both). If the quantity 
or price is positive, this means that CDK has the higher quantity or price. 
If negative, then Stratus has the higher amount. If you were looking at it from
our perspective, a positive number would indicate that to match the quantity or
price of CDK we would have to adjust up our total price/quantity. If negative
we would have to adjust it down (if we didn't want to have to call Stratus and 
just wanted to adjust on our end to match Stratus).

CDK Set Difference:
This shows all parts (along with their quantity and total price) that were
present on the CDK parts list BUT NOT present on the Stratus parts list

Stratus Set Difference:
This shows all parts (along with their quantity and total price) that were
present on the Stratus parts list BUT NOT present on the Stratus parts list.

### Example Input Data

An example of text for input is provided in the test directory so you can see 
exact examples of text that can be provided copy/paste to the program. 
