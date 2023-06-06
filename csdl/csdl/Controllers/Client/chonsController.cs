using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using csdl.Models;

namespace csdl.Controllers.Client
{
    public class chonsController : Controller
    {
        private csdl1Entities db = new csdl1Entities();

        // GET: chons
        public ActionResult Index()
        {
            return View(db.chons.ToList());
        }

        // GET: chons/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            chon chon = db.chons.Find(id);
            if (chon == null)
            {
                return HttpNotFound();
            }
            return View(chon);
        }

        // GET: chons/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: chons/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id_l,the_loai,ten,tien,ghichu")] chon chon)
        {
            if (ModelState.IsValid)
            {
                db.chons.Add(chon);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(chon);
        }

        // GET: chons/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            chon chon = db.chons.Find(id);
            if (chon == null)
            {
                return HttpNotFound();
            }
            return View(chon);
        }

        // POST: chons/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id_l,the_loai,ten,tien,ghichu")] chon chon)
        {
            if (ModelState.IsValid)
            {
                db.Entry(chon).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(chon);
        }

        // GET: chons/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            chon chon = db.chons.Find(id);
            if (chon == null)
            {
                return HttpNotFound();
            }
            return View(chon);
        }

        // POST: chons/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            chon chon = db.chons.Find(id);
            db.chons.Remove(chon);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
