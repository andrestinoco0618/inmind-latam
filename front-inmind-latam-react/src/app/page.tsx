import styles from "./page.module.css";
import Title from '@/components/Title/title';
import Form from "@/components/PsychologicalForm/PsychologicalForm";
import Footer from "@/components/Footer/footer";
import WhatsAppButton from "@/components/ButtonWhatsApp/buttonWhatsApp";

export default function Home() {
  return (
    <div>
    <WhatsAppButton></WhatsAppButton>
      <section className={styles.section__title}>
        <Title></Title>
      </section>
      <section className={styles.section__form}>
        <Form></Form>
      </section>
      <section className={styles.section__footer}>
      <Footer></Footer>
      </section>
    </div>
  );
}
