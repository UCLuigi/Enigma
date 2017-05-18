# enigma

***Background***
- - - -

The device consists of a simple mechanical system of interchangeable rotors that sit side-by-side on a shaft and make electrical contact with each other. In our simulator, there will be a total of 12 rotors:

* Eight rotors labeled with roman numerals I—VIII, of which three will be used in any given configuration as the rightmost rotors,
* Two additional non-moving rotors (Zusatzwalzen) labeled "Beta" and "Gamma", of which one will be used in any configuration, as the fourth-from-right rotor, and
* Two special reflectors (Umkehrwalzen), labeled 'B' and 'C', of which one will be used in any given configuration as the leftmost rotor.

Each of the first ten rotors has 26 contacts on both sides, which are wired together internally so as to effect a permutation of signals coming in from one side onto the contacts on the other (and the inverse permutation when going in the reverse direction). The reflectors are special "rotors" that don't rotate during translation and have all of their contacts on one side. They simply connect half of their contacts to the other half. Figure 2 shows the permutations implemented by all the rotors and reflectors.

A signal starting from the right through one of the 26 possible contacts will flow through wires in four rotors, "bounce" off the reflector, and then come back through the same rotors by a different route, always ending up permuted to a letter position different from where it started (A significant crytographic weakness, as it turned out. It doesn't really do a would-be code-breaker any good to know that some letters in an encrypted message it might be the same as the those in the plaintext if he doesn't know which ones. But it does a great deal of good to be able to eliminate a possible decryption because one position in the encrypted text would have the same letter as the plaintext.).

Each rotor and each reflector implements a different permutation, and the overall effect depends on their configuration: which rotors and reflector are used, what order they are placed in the machine, and which rotational position they are initially set to. This configuration is the secret key used to encrypt or decrypt a message. On our simple machine, there are thus 614175744:

* Two possible reflectors times
* Two possible rotors in the fourth position, times
* 8!/(8−3)!=336 choices for the rightmost three rotors and their ordering, times
* 26^4 possible initial rotational settings for the rightmost four rotors (each reflector has only one possible position.).

In what follows, we'll refer to the selected rotors in a machine's configuration as 1—5, with 1 being the reflector, and 5 the rightmost rotor.

Before a letter of a message is translated, a spring-loaded pawl (lever), one for each of rotors 3—5, tries to engage a ratchet on the right side of its rotor and to rotate that rotor by one position, thus changing the permutation performed by the rotor. The lever on the right rotor (5) always succeeds, so that rotor 5 (the "fast" rotor) rotates one position before each character. The pawls pushing rotors 3 and 4, however, are normally blocked from engaging their rotors by a ring on the left side of the rotor to their right (rotors 4 and 5 respectively). This ring holds the pawl away from its ratchet, preventing its wheel from moving, except when the pawl is positioned over a notch in the ring when the pawl moves. Each of the I—VIII rotors has at least one such notch (two in the case of rotors VI—VIII). A "feature" of the design (It was corrected in other versions of the Enigma, since it reduced the period of the cipher) is that when a pawl is in a notch, it also moves the notch and the wheel it is connected to, so that the rotors on both sides of the pawl move. This means that rotor 4 experiences "double stepping"---if it advances on one step so that its notch moves under the pawl to its left (belonging to rotor 3), then on the next step, when rotor 3 advances, its pawl also advances rotor 4 (even though rotor 4's own pawl has moved off the notch to its right on the previous move and does not contact the ratchet on rotor 4). This peculiarity only affects rotor 4: rotor 5 always advances anyway, and rotors 1 and 2 have no pawls and never move.

The effect of advancing a wheel is to change where on the wheel a certain signal enters or leaves. When a wheel is in its 'A' setting in the machine, then a signal that arrives at, say, the 'C' position, goes into the 'C' contact on the wheel. Likewise, a signal the leaves the wheel from its 'C' contact exits at the 'C' position. When the wheel is rotated by one to its 'B' setting, a signal that arrives at the 'C' position goes instead into the 'D' contact on the wheel, and a signal that leaves through the 'D' contact does so at the 'C' position. It's easier to see if we use numbers 0—25 rather than letters ('A' is 0, 'B' is 1, etc.). Then, when the wheel is in its **k** setting, a signal entering at the **p** position enters the **p + k mod 26** contact on the wheel, and a signal exiting through the **c** contact does so at the **c − k mod 26** position. Figure 1 shows the effect of moving rotor I from its 'A' to its 'B' setting.

The contacts on the rightmost rotor's right side connect with stationary input and output contacts, which run to keys that, when pressed, direct current to the contact from a battery or, when not pressed, direct current back from the contact to a light bulb indicating a letter of the alphabet. Since a letter never encrypts or decrypts to itself, the to and from directions never conflict.

The operator can set the initial positions of the rotors using finger wheels on each rotor. Each rotor has an alphabet running around its perimeter and visible to the operator through the top of the machine. The letter that is showing at any given time indicates the setting of that rotor.

![picture alt](http://www.cs61bl.org/su16/materials/proj/proj1/img/permutation.png)
![picture alt](http://www.cs61bl.org/su16/materials/proj/proj1/img/table.png)


***Input and Output***
- - - -

To run the program, you can use the command
  >>java enigma.Main [input file name] [output file name]
  
The input to your program will consist of a sequence of messages to decode, each preceded by a line giving the initial configuration. A configuration line looks like this:
  >> \* B BETA III IV I AXLE

(all upper case.) This particular example means that the rotors used are reflector B, and rotors Beta, III, IV, and I, with rotor I in the rightmost, or fast, slot. Rotor 1 is always the reflector; rotor 2 is Beta or Gamma, and each of 3—5 is one of rotors I—VIII. A rotor may not be repeated. The last four-letter word gives the initial positions of rotors 2—5 (i.e., not including the reflector,) in the same order as they were specified.

After each configuration line comes a message on any number of lines. Each line of a message consists only of letters, blanks, and tabs (0 or more). The program should ignore the blanks and tabs and convert all letters to upper case. The end of message is indicated either by the end of the input or by a new configuration line (distinguished by its leading asterisk). The machine is not reset between lines, but continues stepping from where it left off on the previous message line. Because the Enigma is a reciprocal cipher, a given translation may either be a decryption or encryption; you don't have to worry about which, since the process is the same in any case.

Output the translation for each message line in groups of five upper-case letters, separated by blanks (the last group may have fewer characters, depending on the message length). Figure 3 contains an example that shows an encryption followed by a decryption of the encrypted message. Since we have yet to cover the details of File I/O, you will be provided the File IO machinery for this project.

![picture alt](http://www.cs61bl.org/su16/materials/proj/proj1/img/input_output.png)
