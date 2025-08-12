# FLEX : Framework for Livestock Empowerment and Decentralized Secure Data eXchange
# Services to Run

## **IPFS**
```bash
docker run -d   --name ipfs-node   -v ipfs_staging:/export   -v ipfs_data:/data/ipfs   -p 4001:4001   -p 5001:5001   -p 8080:8080   ipfs/go-ipfs:latest
```

---

## **VDR**
- **TraceApplication**  
  Used to register users and assign roles. It is a web-based wallet that also manages resources and evidences to be shared through IPFS.
- **VDRApplication**  
  Used to manage users' public credentials and DIDs.

---

## **Farm**
- **AnimalApplication** — Manages animal identity data.  
- **ResourceApplication** — Manages farms and facilities.  
- **PreBirthApplication** — Manages pre-birth events.  
- **HealthApplication** — Manages animal health records, both examined by certified professionals and observed by farm employees.  
- **FeedApplication** — Manages records related to animal feed and their movement.  

---

## **Transporter**
- **TransporterApplication** — Manages carriers, transportation, and records of animals transported to the slaughterhouse.

---

## **Slaughterhouse**
- **SlaughterhouseApplication** — Manages data related to animals received for slaughtering and fetches animal health data from central data sharing points.

---

## **Web Application**
Run Web Module developed in reactjs:
```bash
/flex/webmodules/formasweb/farmdash/tempdash
```
Access via:  

[http://localhost:5001/webui](http://localhost:5001/webui)

