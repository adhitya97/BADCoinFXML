package badcoinfxml;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    public String hash;
    public String previousHash; 
    public String merkleRoot;

    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); 
    public long timeStamp; 
    public int nonce;

    public Block(String previousHash ) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash(); 
    }

    //Calculate new hash based on blocks contents
    public String calculateHash() {
        String calculatedhash = Utilities.applySHAAlgo( 
                        previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) + 
                        merkleRoot
                        );
        return calculatedhash;
    }

    //Increases nonce value until hash target is reached.
    public void mineBlock(int difficulty) {
        merkleRoot = Utilities.getMerkleRoot(transactions);
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring( 0, difficulty).equals(target)) {
                nonce ++;
                hash = calculateHash();
        }
        System.out.println("Block Mined - " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if(transaction == null) return false;		
        if((!"0".equals(previousHash))) {
            if((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction added to the new block");
        return true;
    }
	
}
